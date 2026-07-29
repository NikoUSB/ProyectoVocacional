package com.usbbog.proyectovocacional.backend.application.dto.request

data class RolActividadRequest (
    val idRol: Long,
    val idActividad: Long,
    val activo: Boolean
)