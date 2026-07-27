package com.usbbog.proyectovocacional.backend.domain.repository.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.UsuarioLogin

interface UsuarioRepository{


    fun obtenerTodos():List<Usuario>


    fun obtenerPorId(
        id:Long
    ):Usuario?


    fun obtenerPorCorreo(
        correo:String
    ):Usuario?


    fun obtenerPorDocumento(
        documento:String
    ):Usuario?


    fun obtenerPorNombreUsuario(
        nombreUsuario:String
    ):Usuario?

    fun obtenerPorCorreoONombreUsuario(
        value:String
    ):Usuario?

    fun guardar(
        usuario:Usuario
    ):Usuario


    fun desactivar(
        id:Long
    )

    fun reactivar(
        id:Long
    )

    fun obtenerUsuarioConRol(
        value: String
    ): UsuarioLogin?

}

