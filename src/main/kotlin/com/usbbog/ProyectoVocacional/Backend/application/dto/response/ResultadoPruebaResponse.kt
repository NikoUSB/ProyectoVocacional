package com.usbbog.proyectovocacional.backend.application.dto.response

data class ResultadoPruebaResponse(
    val idPrueba: Long,
    val idAreaPredominante: Long,
    val nombreAreaPredominante: String,
    val programasRecomendados: List<ProgramaAfinidadResponse>,
    val nombreReporte: String
)