package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.ProgramaRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.ProgramaResponse
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa

object ProgramaDtoMapper {

    fun toDomain(request: ProgramaRequest): Programa {

        return Programa(
            id = null,
            nombrePrograma = request.nombrePrograma,
            descripcionPrograma = request.descripcionPrograma,
            urlPrograma = request.urlPrograma,
            idArea = request.idArea,
            pathLogo = request.pathLogo,
            activo = true
        )

    }

    fun toDomain(id: Long, request: ProgramaRequest): Programa {

        return Programa(
            id = id,
            nombrePrograma = request.nombrePrograma,
            descripcionPrograma = request.descripcionPrograma,
            urlPrograma = request.urlPrograma,
            idArea = request.idArea,
            pathLogo = request.pathLogo,
            activo = true
        )

    }

    fun toResponse(domain: Programa): ProgramaResponse {

        return ProgramaResponse(
            id = domain.id,
            nombrePrograma = domain.nombrePrograma,
            descripcionPrograma = domain.descripcionPrograma,
            urlPrograma = domain.urlPrograma,
            idArea = domain.idArea
        )

    }
}