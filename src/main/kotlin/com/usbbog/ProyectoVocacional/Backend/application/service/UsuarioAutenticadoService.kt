package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.LogsRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class UsuarioAutenticadoService (
    private val repository: UsuarioRepository,
) {
    fun obtenerUsuarioAutenticado(): Usuario? {

        val authentication =
            SecurityContextHolder
                .getContext()
                .authentication
                ?: throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "No hay un usuario autenticado."
                )

        val username = authentication.name

        return repository.obtenerPorNombreUsuario(username)
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Usuario autenticado no encontrado."
            )
    }

}