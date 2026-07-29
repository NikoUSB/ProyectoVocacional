package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.RolActividad
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolActividadRepository
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class RolActividadService (private val repository: RolActividadRepository) {

    fun obtenerTodos(): List<RolActividad> {

        val rolActividad = repository.obtenerTodos()

        if (rolActividad.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontró ninguna asociación entre rol y actividad."
            )
        }

        return rolActividad
    }

    fun obtenerPorId(id: Long) : RolActividad{
        val rolActividad = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La asociación entre rol y actividad con id $id no encontrado."
            )

        if (!rolActividad.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La asociación entre rol y actividad se encuentra inactivo."
            )
        }

        return rolActividad
    }

    fun desactivar (id: Long){
        val rolActividad = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La asociación entre rol y actividad con id $id no encontrada."
            )

        if (!rolActividad.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La asociación entre rol y actividad ya se encuentra inactiva."
            )
        }

        repository.desactivar(id)
    }

    fun reactivar (id: Long){
        val rolActividad = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La asociación entre rol y actividad con id $id no encontrada."
            )

        if (!rolActividad.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La asociación entre rol y actividad ya se encuentra activa."
            )
        }

        repository.reactivar(id)
    }


}