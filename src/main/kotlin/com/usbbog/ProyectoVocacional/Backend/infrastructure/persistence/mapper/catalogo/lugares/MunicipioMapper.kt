package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.lugares

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Municipio
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.lugares.MunicipioEntity

object MunicipioMapper {
    fun toDomain(entity: MunicipioEntity): Municipio {

        return Municipio(
            idMunicipio = entity.idMunicipio,
            nombreMunicipio = entity.nombreMunicipio,
            idDepartamento = entity.idDepartamento
        )

    }

    fun toEntity(domain: Municipio): MunicipioEntity {

        return MunicipioEntity(
            idMunicipio = domain.idMunicipio,
            nombreMunicipio = domain.nombreMunicipio,
            idDepartamento = domain.idDepartamento
        )

    }
}