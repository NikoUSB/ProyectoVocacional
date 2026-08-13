package com.usbbog.proyectovocacional.backend.application.service


import com.usbbog.proyectovocacional.backend.application.dto.response.PreguntaPruebaResponse
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.AreaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.PreguntaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PreguntaService (

    private val repository: PreguntaRepository,
    private val programaRepository: ProgramaRepository,
    private val areaRepository: AreaRepository,
    private val logsService: LogsService

) {

    fun obtenerTodos(): List<Pregunta> {

        val programas = repository.obtenerTodos()

        if (programas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron preguntas."
            )
        }

        return programas
    }

    fun obtenerParaPrueba(porArea: Int?): List<PreguntaPruebaResponse> {

        val programas = programaRepository.obtenerTodos()
        val preguntas = repository.obtenerTodos()

        val preguntasPorPrograma = preguntas.groupBy { it.idPrograma }
        val programasPorArea = programas.groupBy { it.idArea }

        val seleccionadas = if (porArea == null) {
            programasPorArea
                .toList()
                .shuffled()
                .flatMap { (_, programasDelArea) ->
                    programasDelArea
                        .shuffled()
                        .flatMap { programa ->
                            preguntasPorPrograma[programa.id!!].orEmpty().shuffled()
                        }
                }
        } else {
            val cantidadPorArea = porArea.coerceAtLeast(1)
            programasPorArea
                .toList()
                .shuffled()
                .flatMap { (_, programasDelArea) ->
                    programasDelArea
                        .shuffled()
                        .flatMap { programa ->
                            preguntasPorPrograma[programa.id!!].orEmpty().shuffled()
                        }
                        .take(cantidadPorArea)
                }
        }

        val mapaProgramas = programas.associateBy { it.id!! }

        return seleccionadas.map { pregunta ->
            val programa = mapaProgramas.getValue(pregunta.idPrograma)
            PreguntaPruebaResponse(
                id = pregunta.id!!,
                codigo = pregunta.codigo,
                enunciado = pregunta.enunciado,
                idPrograma = pregunta.idPrograma,
                nombrePrograma = programa.nombrePrograma,
                idArea = programa.idArea,
                nombreArea = areaRepository.obtenerPorId(programa.idArea)?.nombreArea ?: "Sin área"
            )
        }
    }

    fun obtenerPorId(id: Long): Pregunta {

        val pregunta = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Pregunta con id $id no encontrado."
            )

        if (!pregunta.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La pregunta se encuentra inactiva."
            )
        }

        return pregunta
    }

    fun guardar(pregunta: Pregunta): Pregunta {

        programaRepository.obtenerPorId(pregunta.idPrograma)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El programa seleccionado no existe."
            )



        val preguntaConCodigo = repository.obtenerPorCodigo(pregunta.codigo!!)

        if (preguntaConCodigo != null && preguntaConCodigo.id != pregunta.id) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El código '${pregunta.codigo}' ya está en uso."
            )
        }

        if (pregunta.id != null) {
            val preguntaExistente = repository.obtenerPorId(pregunta.id)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pregunta no encontrada."
                )

            if (!preguntaExistente.activo) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede modificar una pregunta inactiva."
                )
            }
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha actualizado la pregunta ${pregunta.codigo}.",
            estado = true
        )

        return repository.guardar(pregunta)
    }

    fun eliminar(id: Long) {

        val pregunta = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Pregunta con id $id no encontrada."
            )

        if (!pregunta.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La Pregunta ya se encuentra inactiva."
            )
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha eliminado la pregunta ${pregunta.codigo}.",
            estado = true
        )

        repository.eliminar(id)
    }

    fun reactivar(id: Long) {

        val pregunta = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Pregunta con id $id no encontrada."
            )

        if (pregunta.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La Pregunta ya se encuentra activa."
            )
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha reactivado la pregunta ${pregunta.codigo}.",
            estado = true
        )

        repository.reactivar(id)
    }

}