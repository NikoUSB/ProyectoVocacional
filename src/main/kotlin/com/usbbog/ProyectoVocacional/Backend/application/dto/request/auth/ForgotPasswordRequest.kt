package com.usbbog.proyectovocacional.backend.application.dto.request.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ForgotPasswordRequest(

    @field:NotBlank(
        message = "El correo es obligatorio."
    )
    @field:Email(
        message = "El correo no es válido."
    )
    val correo: String

)
