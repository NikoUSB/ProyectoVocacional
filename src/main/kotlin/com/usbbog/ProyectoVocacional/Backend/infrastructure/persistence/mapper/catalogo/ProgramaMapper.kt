package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.ProgramaEntity

object ProgramaMapper {

    fun toDomain(entity: ProgramaEntity): Programa {

        return Programa(
            id = entity.id,
            nombrePrograma = entity.nombrePrograma,
            descripcionPrograma = entity.descripcion,
            urlPrograma = entity.url,
            idArea = entity.idArea,
            activo = entity.activo
        )

    }

    fun toEntity(domain: Programa): ProgramaEntity {

        return ProgramaEntity(
            id = domain.id,
            nombrePrograma = domain.nombrePrograma,
            descripcion = domain.descripcionPrograma,
            url = domain.urlPrograma,
            idArea = domain.idArea,
            activo = domain.activo

        )

    }

}