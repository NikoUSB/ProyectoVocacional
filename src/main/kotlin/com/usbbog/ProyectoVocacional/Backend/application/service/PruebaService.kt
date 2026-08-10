package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.request.evaluacion.PruebaCreateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.AfinidadAreaResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.ProgramaAfinidadResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.ResultadoPruebaResponse
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.AfinidadPrograma
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Prueba
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Reporte
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.AreaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.AfinidadProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.PreguntaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.PruebaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.ReporteRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Service
class PruebaService(

    private val usuarioService: UsuarioService,
    private val pruebaRepository: PruebaRepository,
    private val afinidadProgramaRepository: AfinidadProgramaRepository,
    private val reporteRepository: ReporteRepository,
    private val preguntaRepository: PreguntaRepository,
    private val programaRepository: ProgramaRepository,
    private val areaRepository: AreaRepository

) {

    @Transactional
    fun presentar(request: PruebaCreateRequest): ResultadoPruebaResponse {

        val usuario =
            usuarioService.obtenerUsuarioAutenticado()
//            usuarioRepository.obtenerPorNombreUsuario(nombreUsuario)
//            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado.")

        // 1. Invalidar cualquier prueba vigente anterior de este usuario
        pruebaRepository.obtenerPruebaActivaPorUsuario(usuario.id!!)
            ?.let { pruebaRepository.desactivar(it.id!!) }

        // 2. Validar respuestas contra el catálogo vigente
        val preguntasActivas = preguntaRepository.obtenerTodos()
        val preguntasPorId = preguntasActivas.associateBy { it.id }

        if (request.respuestas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes responder al menos una pregunta."
            )
        }

        val idsVistos = mutableSetOf<Long>()

        request.respuestas.forEach { r ->
            val pregunta = preguntasPorId[r.preguntaId]
                ?: throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La pregunta ${r.preguntaId} no existe o está inactiva."
                )

            if (pregunta.codigo != r.codigoPregunta) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El código enviado para la pregunta ${r.preguntaId} no coincide."
                )
            }

            if (!idsVistos.add(r.preguntaId)) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La pregunta ${r.preguntaId} está duplicada."
                )
            }
        }

        // 3. Crear la prueba
        val prueba = pruebaRepository.guardar(
            Prueba(
                id = null,
                idUsuario = usuario.id,
                fecha = LocalDateTime.now(),
                tiempoInvertido = request.tiempoInvertido,
                versionPrueba = request.versionPrueba,
                satisfaccion = request.satisfaccion?.toShort(),
                activo = true
            )
        )

        // 4. Agrupar por programa y normalizar a 1-100
        val porPrograma = request.respuestas.groupBy { preguntasPorId.getValue(it.preguntaId).idPrograma }

        val afinidades = porPrograma.map { (idPrograma, respuestas) ->
            val n = respuestas.size
            val suma = respuestas.sumOf { it.valor }
            val normalizado = (((suma - n).toDouble() / (n * 3)) * 100)
                .roundToInt()
                .coerceIn(0, 100)

            AfinidadPrograma(
                id = null,
                idPrograma = idPrograma,
                idPrueba = prueba.id!!,
                valorAfinidad = BigDecimal(normalizado),
                activo = true
            )
        }

        afinidadProgramaRepository.guardarTodos(afinidades)

        // 5. Promedio por área
        val programasPorId = afinidades.map { it.idPrograma }.distinct()
            .associateWith { programaRepository.obtenerPorId(it)!! }

        val porArea = afinidades.groupBy { programasPorId.getValue(it.idPrograma).idArea }
        val promedioPorArea = porArea.mapValues { (_, l) -> l.map { it.valorAfinidad.toDouble() }.average() }

        if (promedioPorArea.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes responder al menos una pregunta válida."
            )
        }

        val maxPromedio = promedioPorArea.values.max()
        val empatadas = promedioPorArea.filterValues { it == maxPromedio }.keys

        val idAreaPredominante = if (empatadas.size == 1) {
            empatadas.first()
        } else {
            // Desempate: mejor programa individual del área; luego menor id de área
            empatadas
                .sortedWith(
                    compareByDescending<Long> { porArea.getValue(it).maxOf { a -> a.valorAfinidad.toDouble() } }
                        .thenBy { it }
                )
                .first()
        }

        // 6. Afinidad por área (todas las áreas activas; 0 si no fue evaluada)
        val afinidadPorArea = areaRepository.obtenerTodos()
            .map { area ->
                AfinidadAreaResponse(
                    idArea = area.id!!,
                    nombreArea = area.nombreArea,
                    valorAfinidad = area.id?.let { promedioPorArea[it]?.roundToInt() } ?: 0,
                    perfil = area.perfilPredonimante,
                    descripcionArea = area.descripcionArea,
                    pathLogo = area.pathLogo
                )
            }
            .sortedByDescending { it.valorAfinidad }

        // 7. Top 3 programas globales
        val top3 = afinidades.sortedWith(
            compareByDescending<AfinidadPrograma> { it.valorAfinidad }.thenBy { it.idPrograma }
        ).take(3)

        // 8. Reporte
        val nombreReporte = "U${usuario.documento}P${prueba.id}"

        reporteRepository.guardar(
            Reporte(
                id = null,
                idPrueba = prueba.id ?: throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "La prueba no tiene un id asignado."
                ),
                idAreaPredominante = idAreaPredominante,
                idPrograma1 = top3.getOrNull(0)?.idPrograma ?: 0L,
                idPrograma2 = top3.getOrNull(1)?.idPrograma ?: 0L,
                idPrograma3 = top3.getOrNull(2)?.idPrograma ?: 0L,
                nombreArchivo = nombreReporte,
                activo = true
            )
        )

        val area = areaRepository.obtenerPorId(idAreaPredominante)!!

        val url = "/api/v1/pruebas/${prueba.id}/reporte"

        return ResultadoPruebaResponse(
            idPrueba = prueba.id!!,
            fecha = prueba.fecha,
            idAreaPredominante = area.id!!,
            nombreAreaPredominante = area.nombreArea,
            perfil = area.perfilPredonimante,
            descripcionArea = area.descripcionArea,
            afinidadPorArea = afinidadPorArea,
            programasRecomendados = top3.map {
                val programa = programasPorId.getValue(it.idPrograma)
                ProgramaAfinidadResponse(
                    idPrograma = it.idPrograma,
                    nombrePrograma = programa.nombrePrograma,
                    valorAfinidad = it.valorAfinidad.toInt(),
                    descripcionPrograma = programa.descripcionPrograma,
                    urlPrograma = programa.urlPrograma,
                    pathLogo = programa.pathLogo,
                    nombreArea = areaRepository.obtenerPorId(programa.idArea)?.nombreArea
                )
            },
            nombreReporte = nombreReporte,
            url = url
        )
    }

    private fun esAdmin(): Boolean =
        SecurityContextHolder.getContext().authentication!!.authorities.any {
            it.authority == "ROLE_ROOT" || it.authority == "ROLE_ADMINISTRADOR"
        }

    fun obtenerResultado(idPrueba: Long): ResultadoPruebaResponse {

        val usuario = usuarioService.obtenerUsuarioAutenticado()

        val prueba = pruebaRepository.obtenerPorId(idPrueba)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Prueba no encontrada.")

        if (!esAdmin() && prueba.idUsuario != usuario.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes ver el resultado de otro usuario.")
        }

        val reporte = reporteRepository.obtenerPorPrueba(idPrueba)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Esta prueba aún no tiene reporte.")

        val afinidades = afinidadProgramaRepository.obtenerPorPrueba(idPrueba)

        val top3 = afinidades
            .sortedByDescending { it.valorAfinidad }
            .take(3)

        val area = areaRepository.obtenerPorId(reporte.idAreaPredominante!!)!!

        // Afinidad por área a partir de las afinidades guardadas por programa
        val programasPorId = afinidades.map { it.idPrograma }.distinct()
            .associateWith { programaRepository.obtenerPorId(it) }

        val afinidadPorArea = afinidades
            .mapNotNull { afinidad ->
                val programa = programasPorId[afinidad.idPrograma] ?: return@mapNotNull null
                programa.idArea to afinidad
            }
            .groupBy { it.first }
            .map { (idArea, parejas) ->
                val promedio = (parejas.sumOf { it.second.valorAfinidad.toDouble() } / parejas.size).roundToInt()
                val area = areaRepository.obtenerPorId(idArea)
                AfinidadAreaResponse(
                    idArea = idArea,
                    nombreArea = area?.nombreArea ?: "Sin área",
                    valorAfinidad = promedio,
                    perfil = area?.perfilPredonimante,
                    descripcionArea = area?.descripcionArea,
                    pathLogo = area?.pathLogo
                )
            }
            .sortedByDescending { it.valorAfinidad }

        return ResultadoPruebaResponse(
            idPrueba = idPrueba,
            fecha = prueba.fecha,
            idAreaPredominante = area.id!!,
            nombreAreaPredominante = area.nombreArea,
            perfil = area.perfilPredonimante,
            descripcionArea = area.descripcionArea,
            afinidadPorArea = afinidadPorArea,
            programasRecomendados = top3.map { afinidad ->
                val programa = programaRepository.obtenerPorId(afinidad.idPrograma)
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Programa no encontrado.")
                ProgramaAfinidadResponse(
                    idPrograma = afinidad.idPrograma,
                    nombrePrograma = programa.nombrePrograma,
                    valorAfinidad = afinidad.valorAfinidad.toInt(),
                    descripcionPrograma = programa.descripcionPrograma,
                    urlPrograma = programa.urlPrograma,
                    pathLogo = programa.pathLogo,
                    nombreArea = areaRepository.obtenerPorId(programa.idArea)?.nombreArea
                )
            },
            nombreReporte = reporte.nombreArchivo,
            url = "/api/v1/pruebas/${idPrueba}/reporte"
        )
    }

    fun obtenerMisPruebas(): List<Prueba> {

        val usuario = usuarioService.obtenerUsuarioAutenticado()

        val pruebas = pruebaRepository.obtenerPorUsuario(usuario.id!!)

        if (pruebas.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron pruebas.")

        return pruebas
    }

    fun obtenerPruebasDeUsuario(idUsuario: Long): List<Prueba> {

        val usuario = usuarioService.obtenerPorId(idUsuario)

        val pruebas = pruebaRepository.obtenerPorUsuario(idUsuario)

        if (pruebas.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron pruebas de ${usuario.nombre}.")

        return pruebas
    }


    fun obtenerPorId(id: Long): Prueba {
        val usuario = usuarioService.obtenerUsuarioAutenticado()

        val prueba = pruebaRepository.obtenerPorId(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Prueba no encontrada.")

        if (!esAdmin() && prueba.idUsuario != usuario.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes ver la prueba de otro usuario.")
        }

        return prueba
    }

}