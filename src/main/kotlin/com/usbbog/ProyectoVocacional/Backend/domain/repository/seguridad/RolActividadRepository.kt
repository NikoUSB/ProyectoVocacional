package com.usbbog.proyectovocacional.backend.domain.repository.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.RolActividad

interface RolActividadRepository {

    fun obtenerTodos(): List<RolActividad>

    fun obtenerPorId(id: Long): RolActividad?

    fun obtenerPorIdRol(idRol: Long): List<RolActividad>

    fun desactivar(id: Long)

    fun reactivar(id: Long)

}