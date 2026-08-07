package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Reporte
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.ReporteEntity

object ReporteMapper {

    fun toDomain(entity: ReporteEntity) = Reporte(
        id = entity.id,
        idPrueba = entity.idPrueba,
        idAreaPredominante = entity.idArea,
        idPrograma1 = entity.idPrograma1,
        idPrograma2 = entity.idPrograma2,
        idPrograma3 = entity.idPrograma3,
        nombreArchivo = entity.nombreArchivo,
        activo = entity.activo
    )

    fun toEntity(domain: Reporte) = ReporteEntity(
        id = domain.id,
        idPrueba = domain.idPrueba,
        idArea = domain.idAreaPredominante,
        idPrograma1 = domain.idPrograma1,
        idPrograma2 = domain.idPrograma2,
        idPrograma3 = domain.idPrograma3,
        nombreArchivo = domain.nombreArchivo,
        activo = domain.activo
    )
}