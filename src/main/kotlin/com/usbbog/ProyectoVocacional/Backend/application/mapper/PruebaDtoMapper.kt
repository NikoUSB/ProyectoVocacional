package com.usbbog.proyectovocacional.backend.application.mapper


import com.usbbog.proyectovocacional.backend.application.dto.response.PruebaResponse
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Prueba

object PruebaDtoMapper {

    fun toResponse(domain: Prueba): PruebaResponse {

        return PruebaResponse(
            id = domain.id,
            fecha = domain.fecha,
            tiempoInvertido = domain.tiempoInvertido,
            versionPrueba = domain.versionPrueba,
            satisfaccion = domain.satisfaccion,
            activo = domain.activo
        )

    }

}