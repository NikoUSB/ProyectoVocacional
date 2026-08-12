package com.usbbog.proyectovocacional.backend.domain.repository.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs

interface LogsRepository {

    fun obtenerTodos(): List<Logs>

    fun guardar(Logs: Logs): Logs

}