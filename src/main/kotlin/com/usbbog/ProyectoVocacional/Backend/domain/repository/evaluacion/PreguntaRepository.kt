package com.usbbog.proyectovocacional.backend.domain.repository.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta

interface PreguntaRepository {

    fun obtenerTodos(): List<Pregunta>

    fun obtenerTodosIncluyendoInactivos(): List<Pregunta>

    fun obtenerPorId(id: Long): Pregunta?

    fun obtenerPorCodigo(codigo: String): Pregunta?

    fun guardar(pregunta: Pregunta): Pregunta

    fun eliminar(id: Long)

    fun reactivar(id:Long)

}