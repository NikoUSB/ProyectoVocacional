package com.usbbog.proyectovocacional.backend.application.dto.request.usuario

import jakarta.validation.constraints.*
import java.time.LocalDate

data class UsuarioCreateRequest(

    @field:NotNull(
        message = "El rol es obligatorio."
    )
    val idRol: Long,

    val idPrograma: Long? = null,


    @field:NotBlank(
        message = "El nombre es obligatorio."
    )
    @field:Size(
        max = 100,
        message = "El nombre no puede superar los 100 caracteres."
    )
    val nombre: String,


    @field:NotBlank(
        message = "Los apellidos son obligatorios."
    )
    @field:Size(
        max = 100,
        message = "Los apellidos no pueden superar los 100 caracteres."
    )
    val apellidos: String,


    @field:NotBlank(
        message = "El documento es obligatorio."
    )
    @field:Size(
        max = 30
    )
    val documento: String,


    @field:NotBlank(
        message = "El correo es obligatorio."
    )
    @field:Email(
        message = "El correo no es válido."
    )
    val correo: String,


    @field:NotBlank(
        message = "El nombre de usuario es obligatorio."
    )
    val nombreUsuario: String,


    val telefono: String?,

    val fechaNacimiento: LocalDate?,

    val genero: String?,

    val generoOtro: String?,

    val departamento: String?,

    val ciudad: String?,

    val semestre: Int?,


    @field:NotBlank(
        message = "La contraseña es obligatoria."
    )
    @field:Size(
        min = 8,
        message = "La contraseña debe tener al menos 8 caracteres."
    )
    val contrasena: String

)