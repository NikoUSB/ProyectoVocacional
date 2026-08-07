package com.usbbog.proyectovocacional.backend.application.service


import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.PreguntaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PreguntaService (
    private val repository: PreguntaRepository,
    private val programaRepository: ProgramaRepository
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

        repository.reactivar(id)
    }

}