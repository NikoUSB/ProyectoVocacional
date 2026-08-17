package com.usbbog.proyectovocacional.backend.application.dto.response

data class PreguntaResponse (
    val id: Long?,
    val codigo: String?,
    val idPrograma: Long,
    val enunciado: String,
    val activo: Boolean
)