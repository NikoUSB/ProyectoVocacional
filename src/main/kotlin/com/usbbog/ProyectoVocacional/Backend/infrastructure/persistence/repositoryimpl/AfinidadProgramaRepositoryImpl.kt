package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.AfinidadPrograma
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.AfinidadProgramaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion.AfinidadProgramaMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.AfinidadProgramaJpaRepository
import org.springframework.stereotype.Repository

@Repository
class AfinidadProgramaRepositoryImpl(
    private val jpaRepository: AfinidadProgramaJpaRepository
) : AfinidadProgramaRepository {

    override fun obtenerTodos(): List<AfinidadPrograma> =
        jpaRepository.findAll().map(AfinidadProgramaMapper::toDomain)

    override fun guardarTodos(lista: List<AfinidadPrograma>): List<AfinidadPrograma> =
        jpaRepository.saveAll(lista.map(AfinidadProgramaMapper::toEntity))
            .map(AfinidadProgramaMapper::toDomain)

    override fun obtenerPorPrueba(idPrueba: Long): List<AfinidadPrograma> =
        jpaRepository.findByIdPrueba(idPrueba).map(AfinidadProgramaMapper::toDomain)
}