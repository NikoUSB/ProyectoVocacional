package com.usbbog.proyectovocacional.backend.application.dto.response

data class AfinidadAreaResponse(
    val idArea: Long,
    val nombreArea: String,
    val valorAfinidad: Int,
    val perfil: String? = null,
    val descripcionArea: String? = null,
    val pathLogo: String? = null
)
