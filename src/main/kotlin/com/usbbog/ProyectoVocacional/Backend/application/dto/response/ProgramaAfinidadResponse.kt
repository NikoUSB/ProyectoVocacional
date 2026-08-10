package com.usbbog.proyectovocacional.backend.application.dto.response

data class ProgramaAfinidadResponse(
    val idPrograma: Long,
    val nombrePrograma: String,
    val valorAfinidad: Int,
    val descripcionPrograma: String? = null,
    val urlPrograma: String? = null,
    val pathLogo: String? = null,
    val nombreArea: String? = null
)