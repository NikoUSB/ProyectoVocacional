package com.usbbog.proyectovocacional.backend.domain.repository.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa

interface AreaRepository {

    fun obtenerTodos(): List<Area>

    fun obtenerTodosIncluyendoInactivos(): List<Area>

    fun obtenerPorId(id: Long): Area?

    fun guardar(area: Area): Area

    fun eliminar(id: Long)

    fun reactivar(id:Long)

    fun obtenerProgramasPorArea(id:Long): List<Programa>

}