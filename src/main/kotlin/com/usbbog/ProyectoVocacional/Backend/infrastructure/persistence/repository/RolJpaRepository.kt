package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.RolEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RolJpaRepository : JpaRepository<RolEntity, Long> {

    fun findByActivoTrue(): List<RolEntity>

    fun existsByNombreRolIgnoreCase(nombreRol: String): Boolean

}