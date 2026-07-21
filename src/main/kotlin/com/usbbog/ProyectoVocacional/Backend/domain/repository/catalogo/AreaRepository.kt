package com.usbbog.proyectovocacional.backend.domain.repository.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area

interface AreaRepository {

    fun obtenerTodos(): List<Area>

    fun obtenerPorId(id: Long): Area?

    fun guardar(area: Area): Area

    fun eliminar(id: Long)
}