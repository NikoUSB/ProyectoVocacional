package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Actividad
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.ActividadRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad.ActividadMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.ActividadJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ActividadRepositoryImpl  (
    private val jpaRepository: ActividadJpaRepository
) : ActividadRepository {

    override fun obtenerTodos(): List<Actividad> =
        jpaRepository.findByActivoTrue()
            .map(ActividadMapper::toDomain)

    override fun obtenerPorId(id: Long): Actividad? =
        jpaRepository.findByIdAndActivoTrue(id)
            .map(ActividadMapper::toDomain)
            .orElse(null)

    override fun guardar(actividad: Actividad): Actividad {

        val entity = ActividadMapper.toEntity(actividad)

        return ActividadMapper.toDomain(
            jpaRepository.save(entity)
        )
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

    override fun obtenerPorMetodoYUrl(metodoHttp: String, url: String): Actividad? =
        jpaRepository
            .findByMetodoHttpAndUrlAndActivoTrue(metodoHttp, url)
            ?.let(ActividadMapper::toDomain)

}