package com.usbbog.proyectovocacional.backend.domain.model.evaluacion

import java.time.LocalDateTime

data class Prueba(
    val id: Long?,
    val idUsuario: Long,
    val fecha: LocalDateTime?,
    val tiempoInvertido: Int?,
    val versionPrueba: String?,
    val satisfaccion: Short?,
    val activo: Boolean
)