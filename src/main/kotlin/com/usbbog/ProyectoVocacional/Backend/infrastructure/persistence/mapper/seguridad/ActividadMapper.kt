package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Actividad
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.ActividadEntity

object ActividadMapper {

    fun toDomain(entity: ActividadEntity): Actividad {
        return Actividad(
            id = entity.id,
            nombreActividad = entity.nombreActividad,
            metodoHttp = entity.metodoHttp,
            url = entity.url,
            visible = entity.visible,
            activo = entity.activo
        )
    }

    fun toEntity(model: Actividad): ActividadEntity {
        return ActividadEntity(
            id = model.id,
            nombreActividad = model.nombreActividad,
            metodoHttp = model.metodoHttp,
            url = model.url,
            visible = model.visible,
            activo = model.activo
        )
    }
}