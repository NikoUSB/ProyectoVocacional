package com.usbbog.proyectovocacional.backend.application.dto.request.usuario

import jakarta.validation.constraints.*

data class UsuarioPerfilUpdateRequest(

    val idPrograma: Long? = null,

    @field:NotBlank
    @field:Size(max = 100)
    val nombre: String,

    @field:NotBlank
    @field:Size(max = 100)
    val apellidos: String,

    val telefono: String? = null,

    val genero: String? = null,

    val generoOtro: String? = null,

    val departamento: String? = null,

    val municipio: String? = null,

    val semestre: Int? = null,

)
