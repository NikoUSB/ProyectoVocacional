package com.usbbog.proyectovocacional.backend.infrastructure.security.auth

import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginRequest
import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginResponse
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.PasswordService
import io.swagger.v3.oas.annotations.tags.Tag
import org.h2.schema.Domain
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
    private val passwordService: PasswordService

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



}