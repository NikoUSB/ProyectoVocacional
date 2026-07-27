package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioCreateRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.UsuarioResponse
import com.usbbog.proyectovocacional.backend.application.mapper.UsuarioDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(
    name = "Usuarios",
    description = "Gestión de usuarios del sistema."
)
class UsuarioController(

    private val service: UsuarioService

) {


    @GetMapping
    @Operation(
        summary = "Obtener todos los usuarios."
    )
    fun obtenerTodos(): List<UsuarioResponse> {

        return service.obtenerTodos()
            .map(UsuarioDtoMapper::toResponse)

    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener un usuario por su identificador."
    )
    fun obtenerPorId(

        @PathVariable
        id: Long

    ): UsuarioResponse {

        return UsuarioDtoMapper.toResponse(

            service.obtenerPorId(id)

        )

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Crear un nuevo usuario."
    )
    fun crear(

        @Valid
        @RequestBody
        request: UsuarioCreateRequest

    ): UsuarioResponse {

        return UsuarioDtoMapper.toResponse(

            service.guardar(

                UsuarioDtoMapper.toDomain(
                    request
                )

            )

        )

    }


    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un usuario existente."
    )
    fun actualizar(

        @PathVariable
        id: Long,

        @Valid
        @RequestBody
        request: UsuarioUpdateRequest

    ): UsuarioResponse {

        return UsuarioDtoMapper.toResponse(

            service.guardar(

                UsuarioDtoMapper.toDomain(

                    id,
                    request

                )

            )

        )

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Desactivar un usuario."
    )
    fun eliminar(

        @PathVariable
        id: Long

    ) {

        service.eliminar(id)

    }


    @PatchMapping("/{id}/reactivar")
    @Operation(
        summary = "Reactivar un usuario."
    )
    fun reactivar(

        @PathVariable
        id: Long

    ) {

        service.reactivar(id)

    }


}