package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.ActividadEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ActividadJpaRepository : JpaRepository<ActividadEntity, Long> {

    fun findByIdAndActivoTrue(id: Long): Optional<ActividadEntity>
    fun findByActivoTrue(): List<ActividadEntity>

}