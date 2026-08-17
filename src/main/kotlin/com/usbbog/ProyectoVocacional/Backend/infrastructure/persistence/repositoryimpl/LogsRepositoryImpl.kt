package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.LogsRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad.LogsMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.LogsJpaRepository
import org.springframework.stereotype.Repository

@Repository
class LogsRepositoryImpl(
    private val jpaRepository: LogsJpaRepository
) : LogsRepository {

    override fun obtenerTodos(): List<Logs> =
        jpaRepository.findAllByOrderByFechaDesc()
            .map(LogsMapper::toDomain)

    override fun obtenerPorUsuario(idUsuario: Long): List<Logs> =
        jpaRepository.findAllByIdUsuarioAlteradoOrderByFechaDesc(idUsuario)
            .map(LogsMapper::toDomain)

    override fun guardar(log: Logs): Logs =
        LogsMapper.toDomain(
            jpaRepository.save(LogsMapper.toEntity(log))
        )

}
