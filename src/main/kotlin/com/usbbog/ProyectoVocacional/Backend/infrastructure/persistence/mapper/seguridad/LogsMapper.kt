package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.LogsEntity

object LogsMapper {

    fun toDomain(entity: LogsEntity): Logs {
        return Logs(
            id = entity.id,
            idUsuarioAlterador = entity.idUsuarioAlterado,
            idActividad = entity.idActividad,
            descripcionLog = entity.descripcion,
            fechaLog = entity.fecha,
            activo = entity.activo
        )
    }

    fun toEntity(model: Logs): LogsEntity {
        return LogsEntity(
            id = model.id,
            idUsuarioAlterado = model.idUsuarioAlterador,
            idActividad = model.idActividad,
            descripcion = model.descripcionLog,
            fecha = model.fechaLog,
            activo = model.activo
        )
    }
}
