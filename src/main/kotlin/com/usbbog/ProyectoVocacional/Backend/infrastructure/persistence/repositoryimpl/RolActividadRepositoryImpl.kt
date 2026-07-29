package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.RolActividad
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolActividadRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad.RolActividadMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.RolActividadJpaRepository
import org.springframework.stereotype.Repository

@Repository
class  RolActividadRepositoryImpl (
    private val jpaRepository: RolActividadJpaRepository
) : RolActividadRepository {
    override fun obtenerTodos(): List<RolActividad> {
        return jpaRepository.findByActivoTrue()
            .map(RolActividadMapper::toDomain)
    }

    override fun obtenerPorId(id: Long): RolActividad? {
        return jpaRepository.findById(id)
            .map(RolActividadMapper::toDomain)
            .orElse(null)
    }

    override fun obtenerPorIdRol(idRol: Long): List<RolActividad> {

        val rolActividades = jpaRepository.findByIdRolAndActivoTrue(idRol)
            .map(RolActividadMapper::toDomain)

        return rolActividades
    }

    override fun desactivar(id: Long) {
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

}