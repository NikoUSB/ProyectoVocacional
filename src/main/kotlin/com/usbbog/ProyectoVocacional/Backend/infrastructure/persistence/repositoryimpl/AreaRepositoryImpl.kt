package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.AreaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.AreaMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.ProgramaMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.AreaJpaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.ProgramaJpaRepository
import org.springframework.stereotype.Repository

@Repository
class AreaRepositoryImpl(

    private val jpaRepository: AreaJpaRepository,
    private val programaRepository: ProgramaJpaRepository

) : AreaRepository {

    override fun obtenerTodos(): List<Area> =
        jpaRepository.findByActivoTrue()
            .map(AreaMapper::toDomain)

    override fun obtenerPorId(id: Long): Area? =
        jpaRepository.findById(id)
            .map(AreaMapper::toDomain)
            .orElse(null)


    override fun guardar(area: Area): Area {

        val entity = AreaMapper.toEntity(area)

        return AreaMapper.toDomain(
            jpaRepository.save(entity)
        )
    }

    override fun eliminar(id: Long) {
        val entity = jpaRepository.findById(id)
            .orElseThrow()

        entity.activo = false

        jpaRepository.save(entity)
    }

    override fun reactivar(id: Long) {
        val entity = jpaRepository.findById(id)
            .orElseThrow()

        entity.activo = true

        jpaRepository.save(entity)
    }

    override fun obtenerProgramasPorArea(id: Long): List<Programa> {

        val programas = programaRepository.findByIdAreaAndActivoTrue(id)
            .map(ProgramaMapper::toDomain)

        return programas

    }
}