package com.usbbog.proyectovocacional.backend.domain.model.seguridad

import java.time.LocalDateTime

data class Logs (
    val id: Long?,
    val idUsuarioAlterador: Long,
    val idActividad: Long,
    val descripcionLog: String?,
    val fechaLog: LocalDateTime,
    val activo: Boolean
)