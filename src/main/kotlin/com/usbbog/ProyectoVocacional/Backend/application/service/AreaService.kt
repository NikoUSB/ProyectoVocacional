package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.AreaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AreaService (private val repository: AreaRepository) {
    fun obtenerTodos(): List<Area> {

        val areas = repository.obtenerTodos()

        if (areas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron áreas."
            )
        }

        return areas
    }


    fun obtenerPorId(id: Long): Area {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (!area.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El área se encuentra inactiva."
            )
        }

        return area
    }

    fun guardar(area: Area): Area {

        if (area.id != null) {
            val areaExistente = repository.obtenerPorId(area.id)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Área no encontrada."
                )

            if (!areaExistente.activo) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede modificar un área inactiva."
                )
            }
        }

        return repository.guardar(area)
    }

    fun eliminar(id: Long) {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (!area.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El área ya se encuentra inactiva."
            )
        }

        repository.eliminar(id)
    }

    fun reactivar(id: Long) {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (area.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El área ya se encuentra activa."
            )
        }

        repository.reactivar(id)
    }

    fun obtenerProgramasPorArea(id: Long): List<Programa> {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (!area.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El área se encuentra inactiva."
            )
        }

        val programas = repository.obtenerProgramasPorArea(id)

        if (programas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron programas para el área ${area.nombreArea}."
            )
        }

        return programas
    }

}