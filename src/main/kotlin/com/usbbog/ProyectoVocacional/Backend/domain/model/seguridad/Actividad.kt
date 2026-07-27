package com.usbbog.proyectovocacional.backend.domain.model.seguridad

data class Actividad (
    val id: Long?,
    val nombreActividad: String,
    val url: String?,
    val activo: Boolean
)