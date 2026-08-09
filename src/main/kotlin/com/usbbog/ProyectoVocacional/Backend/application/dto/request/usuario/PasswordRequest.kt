package com.usbbog.proyectovocacional.backend.application.dto.request.usuario

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class PasswordRequest(

    @field:NotBlank
    val passwordActual: String,

    @field:NotBlank
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
        message = "La nueva contraseña debe tener mínimo 8 caracteres, una mayúscula, un número y un símbolo."
    )
    val passwordNueva: String

)