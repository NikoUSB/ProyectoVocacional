package com.usbbog.proyectovocacional.backend.application.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PreguntaRequest (
    @field:NotBlank(message = "El código de la pregunta es obligatorio.")
    val codigo: String,

    @field:NotNull(message = "El programa es obligatorio.")
    val idPrograma: Long,

    @field:NotBlank(message = "El enunciado es obligatorio.")
    val enunciado: String
)