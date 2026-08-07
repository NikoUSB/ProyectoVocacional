package com.usbbog.proyectovocacional.backend.domain.model.evaluacion

data class Reporte(
    val id: Long?,
    val idPrueba: Long,
    val idAreaPredominante: Long,
    val idPrograma1: Long,
    val idPrograma2: Long,
    val idPrograma3: Long,
    val nombreArchivo: String,
    val activo: Boolean
)