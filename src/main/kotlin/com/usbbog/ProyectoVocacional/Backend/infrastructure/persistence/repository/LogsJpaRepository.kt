package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.LogsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LogsJpaRepository: JpaRepository<LogsEntity, Long> {

    //fun findBy

}