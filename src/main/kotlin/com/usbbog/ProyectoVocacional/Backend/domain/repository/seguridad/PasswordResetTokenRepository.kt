package com.usbbog.proyectovocacional.backend.domain.repository.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.PasswordResetToken

interface PasswordResetTokenRepository {

    fun guardar(token: PasswordResetToken): PasswordResetToken

    fun obtenerPorToken(token: String): PasswordResetToken?

    fun invalidarTokensAnteriores(idUsuario: Long)

}
