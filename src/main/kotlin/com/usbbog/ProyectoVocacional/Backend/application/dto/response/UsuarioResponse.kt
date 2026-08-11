package com.usbbog.proyectovocacional.backend.application.dto.response

import java.time.LocalDate
import java.time.LocalDateTime

data class UsuarioResponse(

    val id: Long?,

    val idRol: Long,

    val idPrograma: Long?,

    val nombre: String,

    val apellidos: String,

    val documento: String,

    val correo: String,

    val nombreUsuario: String?,

    val telefono: String?,

    val fechaNacimiento: LocalDate?,

    val genero: String?,

    val generoOtro: String?,

    val departamento: String?,

    val municipio: String?,

    val semestre: Int?,

    val estado:Boolean,

    val fechaCreacion: LocalDateTime?,

    //val fechaActualizacion: LocalDateTime?

)