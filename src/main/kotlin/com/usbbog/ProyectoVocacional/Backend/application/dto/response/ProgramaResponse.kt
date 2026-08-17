package com.usbbog.proyectovocacional.backend.application.dto.response

data class ProgramaResponse (
    val id: Long?,
    val nombrePrograma: String,
    val descripcionPrograma: String?,
    val urlPrograma: String?,
    val idArea: Long,
    val activo: Boolean
)