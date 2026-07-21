package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.AreaEntity
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.RolEntity
import org.springframework.data.jpa.repository.JpaRepository


interface AreaJpaRepository : JpaRepository<AreaEntity, Long>{

    fun findByActivoTrue(): List<AreaEntity>

}