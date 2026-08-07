package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.PreguntaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.lugares.DepartamentoMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.lugares.DepartamentoMapper.toDomain
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion.PreguntaMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.PreguntaJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PreguntaRepositoryImpl(private val jpaRepository: PreguntaJpaRepository) : PreguntaRepository {

    override fun obtenerTodos(): List<Pregunta> =
        jpaRepository.findByActivoTrue()
            .map(PreguntaMapper::toDomain)

    override fun obtenerPorId(id: Long): Pregunta? =
        jpaRepository.findById(id)
            .map(PreguntaMapper::toDomain)
            .orElse(null)

    override fun obtenerPorCodigo(codigo: String): Pregunta? =
        jpaRepository.findByCodigo(codigo)
            ?.let(PreguntaMapper::toDomain)


    override fun guardar(pregunta: Pregunta): Pregunta {

        val entity = PreguntaMapper.toEntity(pregunta)

        return PreguntaMapper.toDomain(
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
}