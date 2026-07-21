package com.usbbog.proyectovocacional.backend.infrastructure.config.exception

import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val message: String,
    val error: String,
    val path: String
)