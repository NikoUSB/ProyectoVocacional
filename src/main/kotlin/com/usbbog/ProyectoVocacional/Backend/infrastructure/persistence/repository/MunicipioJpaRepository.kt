package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.lugares.MunicipioEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MunicipioJpaRepository : JpaRepository<MunicipioEntity, String> {
    fun findByIdDepartamento(idDepartamento: String): List<MunicipioEntity>
}