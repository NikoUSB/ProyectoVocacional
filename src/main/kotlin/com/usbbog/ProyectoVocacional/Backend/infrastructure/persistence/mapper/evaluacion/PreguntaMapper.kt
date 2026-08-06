package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.PreguntaEntity

object PreguntaMapper {

    fun toDomain(entity: PreguntaEntity): Pregunta {

        return Pregunta(
            id = entity.id,
            codigo = entity.codigo,
            idPrograma = entity.idPrograma,
            enunciado = entity.enunciado,
            activo = entity.activo
        )

    }

    fun toEntity(domain: Pregunta): PreguntaEntity {

        return PreguntaEntity(
            id = domain.id,
            codigo = domain.codigo,
            idPrograma = domain.idPrograma,
            enunciado = domain.enunciado,
            activo = domain.activo
        )

    }
    
}