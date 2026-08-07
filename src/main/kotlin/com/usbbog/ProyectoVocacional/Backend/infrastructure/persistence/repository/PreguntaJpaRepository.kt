package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.PreguntaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PreguntaJpaRepository : JpaRepository<PreguntaEntity, Long>{

    fun findByCodigo(codigo: String): PreguntaEntity?

    fun findByActivoTrue(): List<PreguntaEntity>

    fun findByIdProgramaAndActivoTrue(idArea: Long): List<PreguntaEntity>

}