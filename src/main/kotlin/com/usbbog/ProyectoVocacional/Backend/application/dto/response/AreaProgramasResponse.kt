package com.usbbog.proyectovocacional.backend.application.dto.response

data class AreaProgramasResponse(
    val id: Long,
    val nombreArea: String,
    val programas: List<ProgramaCatalogoResponse>
)
