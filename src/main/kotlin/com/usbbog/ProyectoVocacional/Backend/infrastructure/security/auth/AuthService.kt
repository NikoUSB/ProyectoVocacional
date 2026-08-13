package com.usbbog.proyectovocacional.backend.infrastructure.security.auth

import com.usbbog.proyectovocacional.backend.application.service.LogsService
import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginRequest
import com.usbbog.proyectovocacional.backend.infrastructure.security.dto.LoginResponse
import com.usbbog.proyectovocacional.backend.infrastructure.security.jwt.JwtService
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.PasswordService
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import com.usbbog.proyectovocacional.backend.infrastructure.security.jwt.JwtProperties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.slf4j.LoggerFactory

@Service
class AuthService(

    private val usuarioRepository: UsuarioRepository,

    private val rolRepository: RolRepository,

    private val logsService: LogsService,

    private val passwordService: PasswordService,

    private val jwtService: JwtService,

    private val jwtProperties: JwtProperties

) {

//    private val logger =
//
//        LoggerFactory.getLogger(
//            AuthService::class.java
//        )



    fun login(

        request: LoginRequest

    ): LoginResponse {


        val usuario =

            usuarioRepository

                .obtenerUsuarioConRol(request.username)

                ?:  throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas."
                )

        val match = passwordService.matches(

            request.password,
            usuario.contrasenaHash

        )

        if(!match){
            logsService.generarLogLogin(
                usuario = usuario,
                usernameIntentado = request.username,
                descripcion = "ha intentado inicio de sesión.",
                estado = false
            )
            throw ResponseStatusException(

                HttpStatus.UNAUTHORIZED,
                "Credenciales inválidas."

            )

        }


        val token =

            jwtService.generateToken(

                usuario.nombreUsuario!!,

                usuario.rol

            )

        logsService.generarLogLogin(
            usuario = usuario,
            usernameIntentado = request.username,
            descripcion = "ha intentado inicio de sesión.",
            estado = true
        )

        return LoginResponse(

            token = token,

            expiresIn = jwtProperties.expiration / 1000,

            username = usuario.nombreUsuario,

            rol = usuario.rol

        )

    }

}