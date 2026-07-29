package com.usbbog.proyectovocacional.backend.application.dto.response

data class ActividadResponse (
    val id: Long?,
    val nombreActividad: String,
    val metodoHttp: String,
    val url: String?,
    val visible:Boolean,
    val activo: Boolean
)