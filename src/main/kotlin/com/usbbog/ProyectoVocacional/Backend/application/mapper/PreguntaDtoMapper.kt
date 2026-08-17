package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.PreguntaRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.PreguntaResponse
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta

object PreguntaDtoMapper {
    fun toDomain(request: PreguntaRequest): Pregunta {

        return Pregunta(
            id = null,
            codigo = request.codigo,
            idPrograma = request.idPrograma,
            enunciado = request.enunciado,
            activo = true,
        )

    }

    fun toDomain(id: Long, request: PreguntaRequest): Pregunta {

        return Pregunta(
            id = id,
            codigo = request.codigo,
            idPrograma = request.idPrograma,
            enunciado = request.enunciado,
            activo = true
        )

    }

    fun toResponse(domain: Pregunta): PreguntaResponse {

        return PreguntaResponse(
            id = domain.id,
            codigo = domain.codigo,
            idPrograma = domain.idPrograma,
            enunciado = domain.enunciado,
            activo = domain.activo
        )
    }
}