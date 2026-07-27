package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.ProgramaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProgramaJpaRepository : JpaRepository<ProgramaEntity, Long> {

    fun findByActivoTrue(): List<ProgramaEntity>

    fun existsByNombreProgramaIgnoreCase(nombreRol: String): Boolean

}