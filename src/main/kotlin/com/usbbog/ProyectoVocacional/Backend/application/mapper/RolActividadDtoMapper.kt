package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.RolActividadRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.RolActividadResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.RolActividad


object RolActividadDtoMapper {

    fun toDomain(request: RolActividadRequest): RolActividad {

        return RolActividad(
            id = null,
            idRol = request.idRol,
            idActividad = request.idActividad,
            activo = request.activo
        )

    }

    fun toDomain(id: Long, request: RolActividadRequest): RolActividad {

        return RolActividad(
            id = id,
            idRol = request.idRol,
            idActividad = request.idActividad,
            activo = request.activo
        )

    }

    fun toResponse(domain: RolActividad): RolActividadResponse {

        return RolActividadResponse(
            id = domain.id,
            idRol = domain.idRol,
            idActividad = domain.idActividad,
            activo = domain.activo
        )

    }

}