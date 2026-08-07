package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.AfinidadPrograma
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.AfinidadProgramaEntity

object AfinidadProgramaMapper {

    fun toDomain(entity: AfinidadProgramaEntity) = AfinidadPrograma(
        id = entity.id,
        idPrograma = entity.idPrograma,
        idPrueba = entity.idPrueba,
        valorAfinidad = entity.valorAfinidad,
        activo = entity.activo
    )

    fun toEntity(domain: AfinidadPrograma) = AfinidadProgramaEntity(
        id = domain.id,
        idPrograma = domain.idPrograma,
        idPrueba = domain.idPrueba,
        valorAfinidad = domain.valorAfinidad,
        activo = domain.activo
    )
}