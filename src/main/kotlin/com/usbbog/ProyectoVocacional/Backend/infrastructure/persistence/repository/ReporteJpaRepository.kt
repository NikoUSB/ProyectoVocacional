package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.ReporteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReporteJpaRepository : JpaRepository<ReporteEntity, Long> {
    fun findByIdPrueba(idPrueba: Long): ReporteEntity?
}