package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Reporte
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.ReporteRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion.ReporteMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.ReporteJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ReporteRepositoryImpl(
    private val jpaRepository: ReporteJpaRepository
) : ReporteRepository {

    override fun obtenerTodos(): List<Reporte> =
        jpaRepository.findAll().map(ReporteMapper::toDomain)

    override fun guardar(reporte: Reporte): Reporte =
        ReporteMapper.toDomain(jpaRepository.save(ReporteMapper.toEntity(reporte)))

    override fun obtenerPorPrueba(idPrueba: Long): Reporte? =
        jpaRepository.findByIdPrueba(idPrueba)?.let(ReporteMapper::toDomain)
}