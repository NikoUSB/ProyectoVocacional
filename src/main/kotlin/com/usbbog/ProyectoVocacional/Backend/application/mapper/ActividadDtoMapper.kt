package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.response.ActividadResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Actividad

object ActividadDtoMapper {

    fun toResponse(domain: Actividad): ActividadResponse {
        return ActividadResponse(
            id = domain.id,
            nombreActividad = domain.nombreActividad,
            metodoHttp = domain.metodoHttp,
            url = domain.url
        )
    }
}