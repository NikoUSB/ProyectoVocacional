package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Rol
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RolService(
    private val repository: RolRepository
) {

    fun obtenerTodos(): List<Rol> {

        val roles = repository.obtenerTodos()

        if (roles.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron roles."
            )
        }

        return roles
    }

    fun obtenerPorId(id: Long) : Rol{
        val rol = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Rol con id $id no encontrado."
        )

        if (!rol.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El rol se encuentra inactivo."
            )
        }

        return rol
    }


    fun guardar(rol: Rol): Rol {

        if (rol.id != null) {

            repository.obtenerPorId(rol.id)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Rol no encontrado."
                )
        }

        if (repository.existePorNombre(rol.nombreRol)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Ya existe un rol con ese nombre."
            )
        }

        return repository.guardar(rol)
    }

    fun eliminar(id: Long) {

        val rol = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Rol con id $id no encontrado."
            )

        if (!rol.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El rol ya se encuentra inactivo."
            )
        }

        repository.eliminar(id)
    }
}