package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.LogsRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad.LogMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.LogsJpaRepository
import org.springframework.stereotype.Repository

@Repository
class LogsRepositoryImpl (
    private val jpaRepository: LogsJpaRepository
): LogsRepository {

    override fun obtenerTodos(): List<Logs> =
        jpaRepository.findAll()
            .map(LogMapper::toDomain)

    override fun guardar(logs: Logs): Logs {

        val entity = LogMapper.toEntity(logs)

        return LogMapper.toDomain(
            jpaRepository.save(entity)
        )
    }

}