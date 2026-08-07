package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.request.evaluacion.PruebaCreateRequest
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

        if (request.respuestas.size != preguntasActivas.size) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes responder todas las preguntas (${preguntasPorId.size}/${preguntasActivas.size})."
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

        // 6. Top 3 programas globales
        val top3 = afinidades.sortedWith(
            compareByDescending<AfinidadPrograma> { it.valorAfinidad }.thenBy { it.idPrograma }
        ).take(3)

        // 7. Reporte
        val nombreReporte = "U${usuario.documento}P${prueba.id}"

        reporteRepository.guardar(
            Reporte(
                id = null,
                idPrueba = prueba.id?: throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "La prueba no tiene un id asignado."
                ),                                   //espera un Long? pero es Long
                idAreaPredominante = idAreaPredominante,
                idPrograma1 = top3[0].idPrograma,
                idPrograma2 = top3[1].idPrograma,
                idPrograma3 = top3[2].idPrograma ,  //espera un Long pero es Long?
                nombreArchivo = nombreReporte,
                activo = true
            )
        )

        val area = areaRepository.obtenerPorId(idAreaPredominante)!!

        return ResultadoPruebaResponse(
            idPrueba = prueba.id,               //espera un Long pero es Long?
            idAreaPredominante = area.id!!,
            nombreAreaPredominante = area.nombreArea,
            programasRecomendados = top3.map {
                ProgramaAfinidadResponse(
                    it.idPrograma,
                    programasPorId.getValue(it.idPrograma).nombrePrograma,
                    it.valorAfinidad.toInt()
                )
            },
            nombreReporte = nombreReporte
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

        val idsTop3 = listOfNotNull(reporte.idPrograma1, reporte.idPrograma2, reporte.idPrograma3)

        val top3 = afinidadProgramaRepository.obtenerPorPrueba(idPrueba)
            .filter { it.idPrograma in idsTop3 }
            .sortedByDescending { it.valorAfinidad }

        val area = areaRepository.obtenerPorId(reporte.idAreaPredominante!!)!!

        return ResultadoPruebaResponse(
            idPrueba = idPrueba,
            idAreaPredominante = area.id!!,
            nombreAreaPredominante = area.nombreArea,
            programasRecomendados = top3.map {
                val programa = programaRepository.obtenerPorId(it.idPrograma)!!
                ProgramaAfinidadResponse(it.idPrograma, programa.nombrePrograma, it.valorAfinidad.toInt())
            },
            nombreReporte = reporte.nombreArchivo
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