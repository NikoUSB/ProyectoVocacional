package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.AreaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AreaService (
    private val repository: AreaRepository
) {
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

            repository.obtenerPorId(area.id)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Area no encontrada."
                )
        }

        return repository.guardar(area)
    }

    fun eliminar(id: Long) {

        val Area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Area con id $id no encontrada."
            )

        if (!Area.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El area ya se encuentra inactiva."
            )
        }

        repository.eliminar(id)
    }

}