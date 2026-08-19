package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.LogsEntity

object LogsMapper {

    fun toDomain(entity: LogsEntity): Logs {
        return Logs(
            id = entity.id,
            idUsuario = entity.idUsuario,
            idUsuarioAlterado = entity.idUsuarioAlterado,
            idActividad = entity.idActividad,
            descripcionLog = entity.descripcion,
            fechaLog = entity.fecha,
            estado = entity.estado
        )
    }

    fun toEntity(model: Logs): LogsEntity {
        return LogsEntity(
            id = model.id,
            idUsuario = model.idUsuario,
            idUsuarioAlterado = model.idUsuarioAlterado,
            idActividad = model.idActividad,
            descripcion = model.descripcionLog,
            fecha = model.fechaLog,
            estado = model.estado
        )
    }
}
