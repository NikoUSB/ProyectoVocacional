package com.usbbog.proyectovocacional.backend.domain.repository.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Actividad

interface ActividadRepository {

    fun obtenerTodos(): List<Actividad>

    fun obtenerPorId(id: Long): Actividad?

    fun guardar(actividad: Actividad): Actividad

    fun desactivar(id: Long)

    fun reactivar(id: Long)

    fun obtenerPorMetodoYUrl(metodoHttp: String, url: String): Actividad?

}