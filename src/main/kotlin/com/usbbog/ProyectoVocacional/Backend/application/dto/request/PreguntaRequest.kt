package com.usbbog.proyectovocacional.backend.application.dto.request

data class PreguntaRequest (
    val codigo: String?,
    val idPrograma: Long,
    val enunciado: String
)