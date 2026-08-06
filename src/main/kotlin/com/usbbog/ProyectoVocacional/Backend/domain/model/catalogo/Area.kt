package com.usbbog.proyectovocacional.backend.domain.model.catalogo

data class Area (
    val id: Long?,
    val nombreArea: String,
    val perfilPredonimante: String?,
    val descripcionArea: String?,
    val pathLogo: String?,
    val activo: Boolean
)