package com.usbbog.proyectovocacional.backend.domain.model.seguridad

import java.time.LocalDateTime

data class Logs (
    val id: Long?,
    val idUsuario: Long,
    val idUsuarioAlterado: Long?,
    val idActividad: Long,
    val descripcionLog: String?,
    val fechaLog: LocalDateTime,
    val estado: Boolean
)