package com.usbbog.proyectovocacional.backend.application.dto.request.evaluacion

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty

data class PruebaCreateRequest(
    val tiempoInvertido: Int?,
    val versionPrueba: String?,
    @field:Min(1) @field:Max(5) val satisfaccion: Int?,
    @field:NotEmpty(message = "Debes enviar las respuestas de la prueba.")
    val respuestas: List<RespuestaPruebaRequest>
)