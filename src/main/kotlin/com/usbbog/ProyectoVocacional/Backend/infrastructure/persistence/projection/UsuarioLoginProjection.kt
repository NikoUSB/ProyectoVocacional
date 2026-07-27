package com.usbbog.proyectovocacional.backend.infrastructure.persistence.projection

interface UsuarioLoginProjection {

    val id: Long

    val nombreUsuario: String

    val contrasenaHash: String

    val estado: Boolean

    val nombreRol: String

}