package com.usbbog.proyectovocacional.backend.application.dto.request.usuario

import jakarta.validation.constraints.NotBlank

data class PasswordRequest(

    @field:NotBlank
    val passwordActual:String,

    @field:NotBlank
    val passwordNueva:String

)