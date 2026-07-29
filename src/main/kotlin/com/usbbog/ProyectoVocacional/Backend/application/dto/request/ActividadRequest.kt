package com.usbbog.proyectovocacional.backend.application.dto.request

data class ActividadRequest (
    val nombreActividad: String,
    val metodoHttp: String,
    val url: String?,
    val visible:Boolean,
    val activo: Boolean
)