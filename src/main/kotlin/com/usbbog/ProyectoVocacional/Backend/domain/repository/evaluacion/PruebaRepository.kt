package com.usbbog.proyectovocacional.backend.domain.repository.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Prueba

interface PruebaRepository {
    fun obtenerPorId(id: Long): Prueba?
    fun obtenerPruebaActivaPorUsuario(idUsuario: Long): Prueba?
    fun obtenerPorUsuario(idUsuario: Long): List<Prueba>
    fun guardar(prueba: Prueba): Prueba
    fun desactivar(id: Long)
}