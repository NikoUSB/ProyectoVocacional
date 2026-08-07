package com.usbbog.proyectovocacional.backend.domain.repository.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Reporte

interface ReporteRepository {
    fun guardar(reporte: Reporte): Reporte
    fun obtenerPorPrueba(idPrueba: Long): Reporte?
}