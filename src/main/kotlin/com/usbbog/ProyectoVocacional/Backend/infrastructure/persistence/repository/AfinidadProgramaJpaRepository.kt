package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.AfinidadProgramaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AfinidadProgramaJpaRepository : JpaRepository<AfinidadProgramaEntity, Long> {
    fun findByIdPrueba(idPrueba: Long): List<AfinidadProgramaEntity>
}