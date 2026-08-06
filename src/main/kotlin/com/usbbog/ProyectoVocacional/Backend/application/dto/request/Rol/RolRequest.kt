package com.usbbog.proyectovocacional.backend.application.dto.request.Rol

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RolRequest(

    @field:NotBlank(
        message = "El nombre del rol es obligatorio."
    )
    @field:Size(
        max = 100,
        message = "El nombre del rol no puede superar los 100 caracteres."
    )
    val nombreRol: String,

)