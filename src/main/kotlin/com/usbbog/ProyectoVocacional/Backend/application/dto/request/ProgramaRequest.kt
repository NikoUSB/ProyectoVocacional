package com.usbbog.proyectovocacional.backend.application.dto.request

data class ProgramaRequest (
    val nombrePrograma: String,
    val descripcionPrograma: String?,
    val urlPrograma: String?,
    val pathLogo: String?,
    val idArea: Long
)