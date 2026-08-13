package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.AreaEntity

object AreaMapper {

    fun toDomain(entity: AreaEntity): Area {

        return Area(
            id = entity.id,
            nombreArea = entity.nombreArea,
            perfilPredonimante = entity.perfilPredonimante,
            descripcionArea = entity.descripcionArea,
            pathLogo  = entity.pathLogo,
            pachoPath = entity.pachoPath,
            activo = entity.activo
        )

    }

    fun toEntity(domain: Area): AreaEntity {

        return AreaEntity(
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