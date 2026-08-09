package com.usbbog.proyectovocacional.backend.domain.model.seguridad

import java.time.LocalDate
import java.time.LocalDateTime

data class Usuario(
    val id: Long?,
    val idRol: Long,
    val nombreUsuario: String?,
    val contrasenaHash: String,
    val documento: String,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val telefono: String?,
    val fechaNacimiento: LocalDate?,
    val genero: String?,
    val generoOtro: String?,
    val departamento: String?,
    val municipio: String?,
    val idPrograma: Long?,
    val semestre: Int?,
    val fechaCreacion: LocalDateTime?,
    val activo: Boolean,
    //val fechaActualizacion: LocalDateTime?
)