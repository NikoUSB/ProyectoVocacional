package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.PasswordResetTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface PasswordResetTokenJpaRepository :
    JpaRepository<PasswordResetTokenEntity, Long> {

    fun findByToken(
        token: String
    ): PasswordResetTokenEntity?

    @Modifying
    @Query("UPDATE PasswordResetTokenEntity t SET t.usado = true WHERE t.idUsuario = :idUsuario AND t.usado = false")
    fun invalidarTokensAnteriores(idUsuario: Long)

}
