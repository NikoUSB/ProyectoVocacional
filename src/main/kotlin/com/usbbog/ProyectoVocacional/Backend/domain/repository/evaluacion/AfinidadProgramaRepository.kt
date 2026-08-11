package com.usbbog.proyectovocacional.backend.domain.repository.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.AfinidadPrograma

interface AfinidadProgramaRepository {
    fun obtenerTodos(): List<AfinidadPrograma>
    fun guardarTodos(lista: List<AfinidadPrograma>): List<AfinidadPrograma>
    fun obtenerPorPrueba(idPrueba: Long): List<AfinidadPrograma>
}