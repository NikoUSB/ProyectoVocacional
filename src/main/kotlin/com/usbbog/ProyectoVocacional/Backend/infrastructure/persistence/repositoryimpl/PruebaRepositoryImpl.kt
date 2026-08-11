package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Prueba
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.PruebaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion.PruebaMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.PruebaJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PruebaRepositoryImpl(
    private val jpaRepository: PruebaJpaRepository
) : PruebaRepository {

    override fun obtenerTodos(): List<Prueba> =
        jpaRepository.findAll().map(PruebaMapper::toDomain)

    override fun obtenerPorId(id: Long): Prueba? =
        jpaRepository.findById(id).map(PruebaMapper::toDomain).orElse(null)

    override fun obtenerPruebaActivaPorUsuario(idUsuario: Long): Prueba? =
        jpaRepository.findByIdUsuarioAndActivoTrue(idUsuario)?.let(PruebaMapper::toDomain)

    override fun obtenerPorUsuario(idUsuario: Long): List<Prueba> =
        jpaRepository.findByIdUsuarioOrderByFechaDesc(idUsuario).map(PruebaMapper::toDomain)

    override fun guardar(prueba: Prueba): Prueba =
        PruebaMapper.toDomain(jpaRepository.save(PruebaMapper.toEntity(prueba)))

    override fun desactivar(id: Long) {
        val entity = jpaRepository.findById(id).orElseThrow()
        entity.activo = false
        jpaRepository.save(entity)
    }
}