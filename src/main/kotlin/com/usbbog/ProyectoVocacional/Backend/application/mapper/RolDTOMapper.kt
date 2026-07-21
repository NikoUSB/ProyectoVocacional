package com.usbbog.proyectovocacional.backend.application.DTOmapper

import com.usbbog.proyectovocacional.backend.application.dto.request.RolRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.RolResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Rol


object RolDtoMapper {

    fun toDomain(request: RolRequest): Rol {

        return Rol(
            id = null,
            nombreRol = request.nombreRol,
            activo = request.activo
        )

    }

    fun toDomain(id: Long, request: RolRequest): Rol {

        return Rol(
            id = id,
            nombreRol = request.nombreRol,
            activo = request.activo
        )

    }

    fun toResponse(domain: Rol): RolResponse {

        return RolResponse(
            id = domain.id,
            nombreRol = domain.nombreRol
        )

    }

}