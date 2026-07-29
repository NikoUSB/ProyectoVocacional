package com.usbbog.proyectovocacional.backend.application.dto.response

data class RolActividadResponse (
    val id: Long?,
    val idRol: Long,
    val idActividad: Long,
    val activo: Boolean
)