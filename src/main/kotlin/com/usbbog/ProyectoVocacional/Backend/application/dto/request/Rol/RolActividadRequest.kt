package com.usbbog.proyectovocacional.backend.application.dto.request.Rol

import org.jetbrains.annotations.NotNull

data class RolActividadRequest (
    @field:NotNull
    val actividades: List<Long>
)