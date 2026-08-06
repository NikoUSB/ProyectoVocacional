package com.usbbog.proyectovocacional.backend.domain.repository.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Rol

interface RolRepository {

    fun obtenerTodos(): List<Rol>

    fun obtenerPorId(id: Long): Rol?

    fun guardar(rol: Rol): Rol

    fun eliminar(id: Long)

    fun reactivar(id: Long)

    fun existePorNombre(nombre: String): Boolean
}