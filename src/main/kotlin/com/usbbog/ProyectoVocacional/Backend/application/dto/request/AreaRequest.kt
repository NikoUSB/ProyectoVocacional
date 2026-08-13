package com.usbbog.proyectovocacional.backend.application.dto.request

data class AreaRequest (
    val nombreArea: String,
    val perfilPredonimante: String?,
    val descripcionArea: String?,
    val pathLogo: String?,
    val pachoPath: String?
)