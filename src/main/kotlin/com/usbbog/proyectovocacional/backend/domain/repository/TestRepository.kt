package com.usbbog.proyectovocacional.backend.domain.repository

import com.usbbog.proyectovocacional.backend.domain.model.Test
import org.springframework.stereotype.Repository

@Repository
interface TestRepository {
    fun findAll(): List<Test>
}