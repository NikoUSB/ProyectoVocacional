package com.usbbog.proyectovocacional.backend.application.dto.request.usuario

import jakarta.validation.constraints.*
import java.time.LocalDate
import java.time.LocalDateTime

data class UsuarioUpdateRequest(

    @field:NotNull
    val idRol: Long,

    val idPrograma: Long? = null,

    @field:NotBlank
    @field:Size(max = 100)
    val nombre: String,

    @field:NotBlank
    @field:Size(max = 100)
    val apellidos: String,

    @field:NotBlank
    val documento: String,

    @field:NotBlank
    @field:Email
    val correo: String,

    @field:NotBlank
    val nombreUsuario: String,

    val telefono: String?,

    val fechaNacimiento: LocalDate?,

    val genero: String?,

    val generoOtro: String?,

    val departamento: String?,

    val ciudad: String?,

    val semestre: Int?,

    @field:NotNull
    val estado:Boolean

)