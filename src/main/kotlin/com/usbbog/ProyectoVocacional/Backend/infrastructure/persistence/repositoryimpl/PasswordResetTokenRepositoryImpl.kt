package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.PasswordResetToken
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.PasswordResetTokenRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad.PasswordResetTokenMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.PasswordResetTokenJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PasswordResetTokenRepositoryImpl(
    private val jpaRepository: PasswordResetTokenJpaRepository
) : PasswordResetTokenRepository {

    override fun guardar(token: PasswordResetToken): PasswordResetToken {
        return PasswordResetTokenMapper.toDomain(
            jpaRepository.save(PasswordResetTokenMapper.toEntity(token))
        )
    }

    override fun obtenerPorToken(token: String): PasswordResetToken? {
        return jpaRepository.findByToken(token)
            ?.let(PasswordResetTokenMapper::toDomain)
    }

}
