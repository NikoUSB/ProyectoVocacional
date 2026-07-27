package com.usbbog.proyectovocacional.backend.infrastructure.security.auth

import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginRequest
import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginResponse
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.PasswordService
import org.h2.schema.Domain
import org.springframework.web.bind.annotation.*


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

    @GetMapping("/test")
    fun test(): String{

        return passwordService.encode(

            "Password123"

        )

    }

}