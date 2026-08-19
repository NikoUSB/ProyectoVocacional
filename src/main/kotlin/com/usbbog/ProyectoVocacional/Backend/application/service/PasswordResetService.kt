package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.request.auth.ResetPasswordRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.MensajeResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.PasswordResetToken
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.PasswordResetTokenRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import com.usbbog.proyectovocacional.backend.infrastructure.config.AppProperties
import com.usbbog.proyectovocacional.backend.infrastructure.mail.ResetPasswordEmailSender
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.PasswordService
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.TokenHashService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.UUID

@Service
class PasswordResetService(

    private val usuarioRepository: UsuarioRepository,

    private val tokenRepository: PasswordResetTokenRepository,

    private val passwordService: PasswordService,

    private val emailSender: ResetPasswordEmailSender,

    private val appProperties: AppProperties,

    private val logsService: LogsService,

    private val tokenHashService: TokenHashService

) {

    private val log = LoggerFactory.getLogger(PasswordResetService::class.java)

    @Transactional
    fun solicitarRecuperacion(correo: String): MensajeResponse {

        val usuario = usuarioRepository.obtenerPorCorreo(correo)

        if (usuario != null) {

            val idUsuario = usuario.id
                ?: throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible generar el token de recuperación."
                )

            tokenRepository.invalidarTokensAnteriores(idUsuario)

            val rawToken = UUID.randomUUID().toString().replace("-", "")

            val tokenGuardado = tokenRepository.guardar(
                PasswordResetToken(
                    id = null,
                    idUsuario = idUsuario,
                    token = tokenHashService.hash(rawToken),
                    fechaExpiracion = LocalDateTime.now().plusHours(1),
                    usado = false,
                    fechaCreacion = LocalDateTime.now()
                )
            )

            val enlace = buildString {
                append(appProperties.frontendUrl)
                append(appProperties.resetPasswordPath)
                append("?token=")
                append(rawToken)
            }

            val nombreCompleto = listOf(usuario.nombre, usuario.apellidos)
                .filter { !it.isNullOrBlank() }
                .joinToString(" ")

            try {
                emailSender.enviar(
                    destinatario = usuario.correo,
                    nombreCompleto = nombreCompleto,
                    enlace = enlace
                )

                logsService.generarLogNoAutenticado(
                    idUsuario = usuario.id,
                    usuarioAlterado = null,
                    descripcion = "ha intentado reestablecer su contraseña.",
                    estado = true
                )

            } catch (e: Exception) {
                log.error("No fue posible enviar el correo de recuperación a ${usuario.correo}", e)

                logsService.generarLogNoAutenticado(
                    idUsuario = usuario.id,
                    usuarioAlterado = null,
                    descripcion = "ha intentado reestablecer su contraseña.",
                    estado = false
                )
            }
        }

        return MensajeResponse(
            "Si el correo se encuentra registrado, recibirás las instrucciones para restablecer tu contraseña."
        )
    }

    fun restablecerContrasena(request: ResetPasswordRequest): MensajeResponse {

        val tokenHash = tokenHashService.hash(request.token)

        val token = tokenRepository.obtenerPorToken(tokenHash)
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El token de recuperación no es válido."
            )

        if (token.usado || token.fechaExpiracion.isBefore(LocalDateTime.now())) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El token de recuperación ya fue utilizado o expiró."
            )
        }

        val usuario = usuarioRepository.obtenerPorId(token.idUsuario)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario no encontrado."
            )

        usuarioRepository.guardar(
            usuario.copy(
                contrasenaHash = passwordService.encode(request.nuevaContrasena)
            )
        )

        tokenRepository.guardar(
            token.copy(usado = true)
        )

        logsService.generarLogNoAutenticado(
            idUsuario = usuario.id!!,
            usuarioAlterado = null,
            descripcion = "ha reestablecido su contraseña",
            estado = true
        )

        return MensajeResponse(
            "Tu contraseña fue restablecida exitosamente."
        )
    }

}
