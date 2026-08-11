package com.usbbog.proyectovocacional.backend.application.dto.response

import java.time.LocalDateTime

data class ResultadoPruebaResponse(
    val idPrueba: Long,
    val fecha: LocalDateTime?,
    val idAreaPredominante: Long,
    val nombreAreaPredominante: String,
    val perfil: String?,
    val descripcionArea: String?,
    val afinidadPorArea: List<AfinidadAreaResponse>,
    val programasRecomendados: List<ProgramaAfinidadResponse>,
    val nombreReporte: String,
    val url: String?
)
