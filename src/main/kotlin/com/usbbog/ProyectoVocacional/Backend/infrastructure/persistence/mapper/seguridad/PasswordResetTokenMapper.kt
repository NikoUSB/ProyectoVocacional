package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.PasswordResetToken
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.PasswordResetTokenEntity

object PasswordResetTokenMapper {

    fun toEntity(token: PasswordResetToken): PasswordResetTokenEntity {
        return PasswordResetTokenEntity(
            id = token.id,
            idUsuario = token.idUsuario,
            token = token.token,
            fechaExpiracion = token.fechaExpiracion,
            usado = token.usado,
            fechaCreacion = token.fechaCreacion
        )
    }

    fun toDomain(entity: PasswordResetTokenEntity): PasswordResetToken {
        return PasswordResetToken(
            id = entity.id,
            idUsuario = entity.idUsuario,
            token = entity.token,
            fechaExpiracion = entity.fechaExpiracion,
            usado = entity.usado,
            fechaCreacion = entity.fechaCreacion
        )
    }

}
