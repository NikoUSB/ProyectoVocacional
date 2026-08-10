package com.usbbog.proyectovocacional.backend.application.dto.response

data class PreguntaPruebaResponse(
    val id: Long,
    val codigo: String?,
    val enunciado: String,
    val idPrograma: Long,
    val nombrePrograma: String,
    val idArea: Long,
    val nombreArea: String
)
