package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.lugares.DepartamentoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DepartamentoJpaRepository : JpaRepository<DepartamentoEntity, String> {

    fun findByIdDepartamento(departamentoId: String): DepartamentoEntity?

}