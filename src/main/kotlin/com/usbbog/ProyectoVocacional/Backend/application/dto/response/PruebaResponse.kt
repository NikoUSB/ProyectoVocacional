package com.usbbog.proyectovocacional.backend.application.dto.response

import java.time.LocalDateTime

data class PruebaResponse(
    val id: Long?,
    val fecha: LocalDateTime?,
    val tiempoInvertido: Int?,
    val versionPrueba: String?,
    val satisfaccion: Short?,
    val activo: Boolean
)