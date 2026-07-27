package com.usbbog.proyectovocacional.backend.domain.model.seguridad

data class UsuarioLogin(

    val id: Long,

    val nombreUsuario: String,

    val contrasenaHash: String,

    val rol: String,

    val activo: Boolean

)