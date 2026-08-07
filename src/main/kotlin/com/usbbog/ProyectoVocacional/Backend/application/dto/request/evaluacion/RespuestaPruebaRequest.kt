package com.usbbog.proyectovocacional.backend.application.dto.request.evaluacion

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

data class RespuestaPruebaRequest(
    @field:NotNull val preguntaId: Long,
    @field:NotBlank val codigoPregunta: String,
    @field:Min(1) @field:Max(4) val valor: Int
)