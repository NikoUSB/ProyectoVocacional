package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.lugares

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.lugares.DepartamentoEntity

object DepartamentoMapper {
    fun toDomain(entity: DepartamentoEntity): Departamento {

        return Departamento(
            idDepartamento = entity.idDepartamento,
            nombreDepartamento = entity.nombreDepartamento
        )

    }

    fun toEntity(domain: Departamento): DepartamentoEntity {

        return DepartamentoEntity(
            idDepartamento = domain.idDepartamento,
            nombreDepartamento = domain.nombreDepartamento
        )

    }
}