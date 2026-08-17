package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl


import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Pregunta
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.ProgramaMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion.PreguntaMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.PreguntaJpaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.ProgramaJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProgramaRepositoryImpl (
    private val jpaRepository: ProgramaJpaRepository,
    private val preguntaRepository: PreguntaJpaRepository
) : ProgramaRepository {

    override fun obtenerTodos(): List<Programa> =
        jpaRepository.findByActivoTrue()
            .map(ProgramaMapper::toDomain)

    override fun obtenerTodosIncluyendoInactivos(): List<Programa> =
        jpaRepository.findAll()
            .map(ProgramaMapper::toDomain)

    override fun obtenerPorId(id: Long): Programa? =
        jpaRepository.findById(id)
            .map(ProgramaMapper::toDomain)
            .orElse(null)


    override fun guardar(programa: Programa): Programa {

        val entity = ProgramaMapper.toEntity(programa)

        return ProgramaMapper.toDomain(
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

    override fun obtenerPreguntasPorPrograma(id: Long): List<Pregunta> {
        val preguntas = preguntaRepository.findByIdProgramaAndActivoTrue(id)
            .map(PreguntaMapper::toDomain)

        return preguntas
    }

}