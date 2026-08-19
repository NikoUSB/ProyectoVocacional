package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProgramaService (

    private val repository: ProgramaRepository,
    private val logsService: LogsService

) {

    fun obtenerTodos(): List<Programa> {

        val programas = repository.obtenerTodos()

        if (programas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron programas."
            )
        }

        return programas
    }

    fun obtenerTodosIncluyendoInactivos(): List<Programa> {
        return repository.obtenerTodosIncluyendoInactivos()
    }


    fun obtenerPorId(id: Long): Programa {

        val programa = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Programa con id $id no encontrado."
            )

        if (!programa.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El programa se encuentra inactivo."
            )
        }

        return programa
    }

    fun guardar(programa: Programa): Programa {

        if (programa.id != null) {
            val programaExistente = repository.obtenerPorId(programa.id)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Programa no encontrado."
                )

            if (!programaExistente.activo) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede modificar un programa inactivo."
                )
            }
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha actualizado el programa ${programa.nombrePrograma}.",
            estado = true
        )

        return repository.guardar(programa)
    }

    fun eliminar(id: Long) {

        val programa = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Programa con id $id no encontrado."
            )

        if (!programa.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El Programa ya se encuentra inactivo."
            )
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha eliminado el programa ${programa.nombrePrograma}",
            estado = true
        )

        repository.eliminar(id)
    }

    fun reactivar(id: Long) {

        val programa = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Programa con id $id no encontrado."
            )

        if (programa.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El Programa ya se encuentra activo."
            )
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha reactivado el programa ${programa.nombrePrograma}",
            estado = true
        )

        repository.reactivar(id)
    }

    fun obtenerPreguntasPorPrograma(id: Long): List<Pregunta> {

        val programa = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Programa con id $id no encontrado."
            )

        if (!programa.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El programa se encuentra inactivo."
            )
        }

        val pregutnas = repository.obtenerPreguntasPorPrograma(id)

        if (pregutnas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron preguntas para el programa ${programa.nombrePrograma}."
            )
        }

        return pregutnas
    }
}