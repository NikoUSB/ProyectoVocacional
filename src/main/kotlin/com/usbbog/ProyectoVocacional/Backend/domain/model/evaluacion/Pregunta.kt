package com.usbbog.proyectovocacional.backend.domain.model.evaluacion

data class Pregunta (
    val id: Long?,
    val codigo: String?,
    val idPrograma: Long,
    val enunciado: String,
    val activo: Boolean
)