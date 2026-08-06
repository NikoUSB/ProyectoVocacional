package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Municipio
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.LugarRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.lugares.DepartamentoEntity
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.lugares.DepartamentoMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.lugares.MunicipioMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.DepartamentoJpaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.MunicipioJpaRepository
import org.springframework.stereotype.Repository

@Repository
class LugarRepositoryImpl (
    private val departamentoRepository: DepartamentoJpaRepository,
    private val municipioRepository: MunicipioJpaRepository
) : LugarRepository {
    override fun obtenerDepartamentos(): List<Departamento> {
        return departamentoRepository.findAll()
            .map(DepartamentoMapper::toDomain)
    }

    override fun obtenerDepartamentoPorId(idDepartamento: String): Departamento? =
        departamentoRepository.findByIdDepartamento(idDepartamento)
            ?.let(DepartamentoMapper::toDomain)

    override fun obtenerMunicipiosPorDepartamento(idDepartamento: String): List<Municipio> {
        return municipioRepository.findByIdDepartamento(idDepartamento)
            .map(MunicipioMapper::toDomain)
    }


}