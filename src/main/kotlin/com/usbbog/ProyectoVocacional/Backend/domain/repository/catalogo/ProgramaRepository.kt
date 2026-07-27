package com.usbbog.proyectovocacional.backend.domain.repository.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa

interface ProgramaRepository {

    fun obtenerTodos(): List<Programa>

    fun obtenerPorId(id: Long): Programa?

    fun guardar(programa: Programa): Programa

    fun eliminar(id: Long)
}
