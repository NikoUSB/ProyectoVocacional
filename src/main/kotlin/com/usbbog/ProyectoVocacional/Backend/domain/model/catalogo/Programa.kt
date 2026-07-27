package com.usbbog.proyectovocacional.backend.domain.model.catalogo

data class Programa (
    val id: Long?,
    val nombrePrograma: String,
    val descripcionPrograma: String?,
    val urlPrograma: String?,
    val idArea: Long,
    val activo: Boolean
)