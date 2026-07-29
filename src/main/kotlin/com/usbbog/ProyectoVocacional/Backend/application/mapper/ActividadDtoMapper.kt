package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.ActividadRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.ActividadResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Actividad

object ActividadDtoMapper {

    fun toDomain(request: ActividadRequest): Actividad {
        return Actividad(
            id = null,
            nombreActividad = request.nombreActividad,
            metodoHttp = request.metodoHttp,
            url = request.url,
            visible = request.visible,
            activo = request.activo
        )
    }

    fun toDomain(id: Long, request: ActividadRequest): Actividad {
        return Actividad(
            id = id,
            nombreActividad = request.nombreActividad,
            metodoHttp = request.metodoHttp,
            url = request.url,
            visible = request.visible,
            activo = request.activo
        )
    }

    fun toResponse(domain: Actividad): ActividadResponse {
        return ActividadResponse(
            id = domain.id,
            nombreActividad = domain.nombreActividad,
            metodoHttp = domain.metodoHttp,
            url = domain.url,
            visible = domain.visible,
            activo = domain.activo
        )
    }


}