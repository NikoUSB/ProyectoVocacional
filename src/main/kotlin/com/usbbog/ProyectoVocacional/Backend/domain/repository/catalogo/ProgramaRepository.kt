package com.usbbog.proyectovocacional.backend.domain.repository.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta

interface ProgramaRepository {

    fun obtenerTodos(): List<Programa>

    fun obtenerTodosIncluyendoInactivos(): List<Programa>

    fun obtenerPorId(id: Long): Programa?

    fun guardar(programa: Programa): Programa

    fun eliminar(id: Long)

    fun reactivar(id:Long)

    fun obtenerPreguntasPorPrograma(id:Long): List<Pregunta>
}
