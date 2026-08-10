package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.PasswordResetTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordResetTokenJpaRepository :
    JpaRepository<PasswordResetTokenEntity, Long> {

    fun findByToken(
        token: String
    ): PasswordResetTokenEntity?

}
