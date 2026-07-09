package com.usbbog.proyectovocacional.backend.application.service

import org.springframework.stereotype.Service
import com.usbbog.proyectovocacional.backend.domain.repository.TestRepository
import com.usbbog.proyectovocacional.backend.application.dto.TestDTO

@Service
class GetTestsService(
    private val testRepository: TestRepository
) {
    fun execute(): List<TestDTO> {
        return testRepository.findAll().map {
            TestDTO(it.id, it.name)
        }
    }
}