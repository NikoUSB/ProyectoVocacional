package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.AreaEntity

object AreaMapper {

    fun toDomain(entity: AreaEntity): Area {

        return Area(
            id = entity.id,
            nombreArea = entity.nombreArea,
            descripcionArea = entity.descripcionArea,
            activo = entity.activo
        )

    }

    fun toEntity(domain: Area): AreaEntity {

        return AreaEntity(
            id = domain.id,
            nombreArea = domain.nombreArea,
            descripcionArea = domain.descripcionArea,
            activo = domain.activo

        )

    }
}