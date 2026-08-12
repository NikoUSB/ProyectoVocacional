package com.usbbog.proyectovocacional.backend.application.dto.response

import java.time.LocalDateTime

data class LogsResponse (
    val fecha: LocalDateTime,
    val log: String,
    val estado: Boolean
)