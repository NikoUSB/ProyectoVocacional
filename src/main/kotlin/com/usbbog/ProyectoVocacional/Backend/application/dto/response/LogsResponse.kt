package com.usbbog.proyectovocacional.backend.application.dto.response

import java.time.LocalDateTime

data class LogsResponse(
    val id: Long?,
    val idUsuarioAlterado: Long,
    val nombreUsuario: String?,
    val idActividad: Long,
    val nombreActividad: String?,
    val descripcion: String?,
    val fecha: LocalDateTime
)
