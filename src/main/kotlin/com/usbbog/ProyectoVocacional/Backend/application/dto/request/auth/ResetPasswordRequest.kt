package com.usbbog.proyectovocacional.backend.application.dto.request.auth

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ResetPasswordRequest(

    @field:NotBlank(
        message = "El token de recuperación es obligatorio."
    )
    val token: String,

    @field:NotBlank(
        message = "La nueva contraseña es obligatoria."
    )
    @field:Size(
        min = 8,
        message = "La nueva contraseña debe tener al menos 8 caracteres."
    )
    val nuevaContrasena: String

)
