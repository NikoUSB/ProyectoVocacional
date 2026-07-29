package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.RolActividadEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RolActividadJpaRepository : JpaRepository<RolActividadEntity, Long> {

    fun findByActivoTrue(): List<RolActividadEntity>

    fun findByIdRolAndActivoTrue(idRol: Long): List<RolActividadEntity>
}