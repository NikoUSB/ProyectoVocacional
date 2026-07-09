package com.usbbog.proyectovocacional.backend.infrastructure.persistence

import com.usbbog.proyectovocacional.backend.domain.model.Test
import com.usbbog.proyectovocacional.backend.domain.repository.TestRepository
import org.springframework.stereotype.Repository

@Repository
class TestRepositoryMock : TestRepository {

    override fun findAll(): List<Test> {
        return listOf(
            Test(1, "Test Vocacional Básico"),
            Test(2, "Test de Habilidades")
        )
    }
}