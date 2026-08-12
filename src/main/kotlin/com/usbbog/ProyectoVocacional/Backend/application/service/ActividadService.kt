package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Actividad
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.ActividadRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolActividadRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.HandlerMapping

@Service
class ActividadService (
    private val repository: ActividadRepository,
    private val rolActividadRepository: RolActividadRepository,
    private val rolRepository: RolRepository,
    private val request: HttpServletRequest,
) {

    fun obtenerTodos(): List<Actividad> {

        val actividades = repository.obtenerTodos()

        if (actividades.isEmpty()) {

            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron áreas."
            )
        }
        return actividades

    }

    fun obtenerPorId(id: Long): Actividad {

        val actividad = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Actividad con id $id no encontrada."
            )

        if (!actividad.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La actividad se encuentra inactiva."
            )
        }

        return actividad

    }

    fun obtenerActividadesPorRolId(idRol: Long): List<Actividad>{

        val rol = rolRepository.obtenerPorId(idRol)
        if (rol == null) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se enccuentra el rol con id ${idRol}."
            )
        }

        val rolActividades = rolActividadRepository.obtenerPorIdRol(idRol)

        if (rolActividades.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron actividades asociadas a ${rol?.nombreRol}."
            )
        }

        val actividades = mutableListOf<Actividad>()

        for (rolActividad in rolActividades) {
            val actividad = repository.obtenerPorId(rolActividad.idActividad)
//                ?: throw ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "No se encontró la actividad con id ${rolActividad.idActividad}."
//                )
            if (actividad != null && actividad.visible == true) {
                actividades.add(actividad)
            }

        }

        return actividades
    }

    fun obtenerActividadActual(): Actividad {

        val metodoHttp = request.method

        val url = request.getAttribute(
            HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE
        ) as? String
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No fue posible determinar la ruta de la actividad actual."
            )

//        println("MÉTODO: $metodoHttp")
//        println("URL: $url")

        return repository.obtenerPorMetodoYUrl(
            metodoHttp,
            url
        ) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No se encontró una actividad para $metodoHttp $url."
        )
    }

}