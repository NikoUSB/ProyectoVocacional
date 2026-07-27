package com.usbbog.proyectovocacional.backend.domain.model.seguridad

data class RolActividad (
    val id: Long?,
    val idRol: Long,
    val idActividad: Long,
    val activo: Boolean
)