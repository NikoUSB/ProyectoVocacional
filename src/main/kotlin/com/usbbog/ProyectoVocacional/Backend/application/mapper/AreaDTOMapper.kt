package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.AreaRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.AreaResponse
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area

object AreaDtoMapper {

    fun toDomain(request: AreaRequest): Area {

        return Area(
            id = null,
            nombreArea = request.nombreArea,
            perfilPredonimante = request.perfilPredonimante,
            descripcionArea = request.descripcionArea,
            pathLogo = request.pathLogo,
            pachoPath = request.pachoPath,
            activo = true
        )

    }

    fun toDomain(id: Long, request: AreaRequest): Area {

        return Area(
            id = id,
            nombreArea = request.nombreArea,
            perfilPredonimante = request.perfilPredonimante,
            descripcionArea = request.descripcionArea,
            pathLogo = request.pathLogo,
            pachoPath = request.pachoPath,
            activo = true
        )

    }

    fun toResponse(domain: Area): AreaResponse {

        return AreaResponse(
            id = domain.id,
            nombreArea = domain.nombreArea,
            perfilPredonimante = domain.perfilPredonimante,
            descripcionArea = domain.descripcionArea,
            pathLogo = domain.pathLogo,
            pachoPath = domain.pachoPath,
            activo = domain.activo
        )

    }

}