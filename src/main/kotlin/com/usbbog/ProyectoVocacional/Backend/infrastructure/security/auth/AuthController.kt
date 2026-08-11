package com.usbbog.proyectovocacional.backend.infrastructure.security.auth

import com.usbbog.proyectovocacional.backend.application.dto.request.auth.ForgotPasswordRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.auth.ResetPasswordRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioCreateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.MensajeResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.usuario.UsuarioResponse
import com.usbbog.proyectovocacional.backend.application.mapper.UsuarioDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.PasswordResetService
import com.usbbog.proyectovocacional.backend.application.service.UsuarioService
import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginRequest
import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginResponse
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.PasswordService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Tag(
    name = "Auth",
    description = "Endpoints publicos para autenticacion y recuperacion de contraseña"
)
@RestController
@RequestMapping(
    "/api/v1/auth"
)
class AuthController(

    private val authService: AuthService,
    private val usuarioService: UsuarioService,
    private val usuarioDtoMapper: UsuarioDtoMapper,
    private val passwordService: PasswordService,
    private val passwordResetService: PasswordResetService

) {

    @PostMapping(
        "/login"
    )
    fun login(

        @RequestBody
        request: LoginRequest

    ): LoginResponse {

        return authService.login(
            request
        )

    }

    //register
    //cambio de contraseña

    @PostMapping(
        "/register"
    )
    fun register(

        @Valid
        @RequestBody
        request: UsuarioCreateRequest

    ): UsuarioResponse {

        return usuarioDtoMapper.toResponse(

            usuarioService.guardar(

                usuarioDtoMapper.toDomain(
                    request
                )

            )
        )

    }

    @Operation(
        summary = "Solicitar la recuperación de contraseña con el correo registrado."
    )
    @PostMapping(
        "/forgot-password"
    )
    fun forgotPassword(

        @Valid
        @RequestBody
        request: ForgotPasswordRequest

    ): MensajeResponse {

        return passwordResetService.solicitarRecuperacion(
            request.correo
        )

    }

    @Operation(
        summary = "Restablecer la contraseña usando el token recibido por correo."
    )
    @PostMapping(
        "/reset-password"
    )
    fun resetPassword(

        @Valid
        @RequestBody
        request: ResetPasswordRequest

    ): MensajeResponse {

        return passwordResetService.restablecerContrasena(
            request
        )

    }

}
