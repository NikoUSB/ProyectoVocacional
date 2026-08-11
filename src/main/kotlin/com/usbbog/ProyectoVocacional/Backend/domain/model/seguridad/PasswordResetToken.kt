package com.usbbog.proyectovocacional.backend.domain.model.seguridad

import java.time.LocalDateTime

data class PasswordResetToken(
    val id: Long?,
    val idUsuario: Long,
    val token: String,
    val fechaExpiracion: LocalDateTime,
    val usado: Boolean,
    val fechaCreacion: LocalDateTime
)
