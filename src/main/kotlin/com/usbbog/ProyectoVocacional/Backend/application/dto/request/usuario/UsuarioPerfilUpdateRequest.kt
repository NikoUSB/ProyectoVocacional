package com.usbbog.proyectovocacional.backend.application.dto.request.usuario

import jakarta.validation.constraints.*

data class UsuarioPerfilUpdateRequest(

    @field:Size(max = 100)
    val nombre: String?,

    @field:Size(max = 100)
    val apellidos: String?,

    val telefono: String?,

    val genero: String?,

    val generoOtro: String?,

    val departamento: String?,

    val municipio: String?,

    val idPrograma: Long?,

    val semestre: Int?

)