package com.usbbog.proyectovocacional.backend.application.dto.response

data class AreaResponse (
    val id: Long?,
    val nombreArea: String,
    val perfilPredonimante: String?,
    val descripcionArea: String?,
    val pathLogo: String?,
    val pachoPath: String?,
    val activo: Boolean
)