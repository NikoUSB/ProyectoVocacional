package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.PruebaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PruebaJpaRepository : JpaRepository<PruebaEntity, Long> {
    fun findByIdUsuarioAndActivoTrue(idUsuario: Long): PruebaEntity?
    fun findByIdUsuarioOrderByFechaDesc(idUsuario: Long): List<PruebaEntity>
}