package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.RolRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.RolResponse
import com.usbbog.proyectovocacional.backend.application.DTOmapper.RolDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.RolService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Tag(
    name = "Roles",
    description = "Gestión de roles y permisos del sistema"
)
@RestController
@RequestMapping("/api/v1/roles")
class RolController(

    private val rolService: RolService

) {

    @GetMapping
    fun obtenerTodos(): List<RolResponse> {

        return rolService.obtenerTodos()
            .map(RolDtoMapper::toResponse)

    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): RolResponse {
        println("ENTRÓ AL CONTROLLER DE ROLES")
        val rol = rolService.obtenerPorId(id)

        return RolDtoMapper.toResponse(rol)

    }

    @GetMapping("/test-auth")
    fun testAuth(
        authentication: Authentication?
    ): String {

        return """
        Usuario: ${authentication?.name}
        Roles: ${authentication?.authorities}
    """.trimIndent()

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crear(
        @Valid
        @RequestBody request: RolRequest
    ): RolResponse {

        val rol = RolDtoMapper.toDomain(request)

        val creado = rolService.guardar(rol)

        return RolDtoMapper.toResponse(creado)

    }


    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Long,
        @Valid
        @RequestBody request: RolRequest
    ): RolResponse {

        val rol = RolDtoMapper.toDomain(id, request)

        val actualizado = rolService.guardar(rol)

        return RolDtoMapper.toResponse(actualizado)

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun eliminar(
        @PathVariable id: Long
    ) {

        rolService.eliminar(id)

    }

}