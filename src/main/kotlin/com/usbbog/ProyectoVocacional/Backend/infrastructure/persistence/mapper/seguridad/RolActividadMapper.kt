package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.RolActividad
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.RolActividadEntity

object RolActividadMapper {

    fun toDomain(entity: RolActividadEntity): RolActividad{
        return RolActividad(
            id = entity.id,
            idRol = entity.idRol,
            idActividad = entity.idActividad,
            activo = entity.activo
        )
    }

    fun toEntity(model: RolActividad): RolActividadEntity {
        return RolActividadEntity(
            id = model.id,
            idRol = model.idRol,
            idActividad = model.idActividad,
            activo = model.activo
        )
    }
}