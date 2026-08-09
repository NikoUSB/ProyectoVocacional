package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.PasswordRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioPerfilUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioRolUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.PruebaResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.usuario.UsuarioPerfilResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.usuario.UsuarioResponse
import com.usbbog.proyectovocacional.backend.application.mapper.PruebaDtoMapper
import com.usbbog.proyectovocacional.backend.application.mapper.UsuarioDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.PruebaService
import com.usbbog.proyectovocacional.backend.application.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(
    name = "Usuarios",
    description = "Gestión de usuarios del sistema."
)
class UsuarioController(

    private val service: UsuarioService,
    private val pruebaService: PruebaService,
    private val usuarioDtoMapper: UsuarioDtoMapper

) {

    @GetMapping
    @Operation(
        summary = "Obtener todos los usuarios."
    )
    fun obtenerTodos(): List<UsuarioResponse> {

        return service.obtenerTodos()
            .map(usuarioDtoMapper::toResponse)
    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener un usuario por su identificador."
    )
    fun obtenerPorId(
        @PathVariable id: Long
    ): UsuarioResponse {

        return usuarioDtoMapper.toResponse(
            service.obtenerPorId(id)
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Desactivar un usuario."
    )
    fun eliminar(
        @PathVariable id: Long
    ) {

        service.eliminar(id)
    }


    @PatchMapping("/{id}/reactivar")
    @Operation(
        summary = "Reactivar un usuario."
    )
    fun reactivar(
        @PathVariable id: Long
    ) {

        service.reactivar(id)
    }


    @PreAuthorize("hasRole('ROOT')")
    @PatchMapping("/{id}/rol")
    @Operation(
        summary = "Actualizar rol de un usuario."
    )
    fun actualizarRol(
        @PathVariable id: Long,
        @Valid @RequestBody request: UsuarioRolUpdateRequest
    ): UsuarioResponse {

        return usuarioDtoMapper.toResponse(
            service.actualizarRol(id, request.idRol)
        )
    }


    @PreAuthorize("hasRole('ROOT') or hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}/pruebas")
    fun obtenerPruebasDeUsuario(
        @PathVariable id: Long
    ): List<PruebaResponse> {

        return pruebaService.obtenerPruebasDeUsuario(id)
            .map(PruebaDtoMapper::toResponse)
    }



    //Propios del usuario
    @GetMapping("/me")
    @Operation(
        summary = "Obtener el perfil del usuario autenticado."
    )
    fun obtenerPerfilPropio(): UsuarioPerfilResponse {

        return usuarioDtoMapper.toPerfilResponse(
            service.obtenerUsuarioAutenticado()
        )
    }

    @PutMapping("/me/perfil")
    @Operation(
        summary = "Actualizar datos del usuario autenticado."
    )
    fun actualizarPerfil(
        @Valid @RequestBody request: UsuarioPerfilUpdateRequest
    ): UsuarioPerfilResponse {

        return usuarioDtoMapper.toPerfilResponse(service.actualizarPerfil(request))
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Desactivar la cuenta del usuario autenticado."
    )
    fun eliminarPerfilPropio() {

        service.eliminarPerfilPropio()
    }

    @PostMapping("/me/cambio-password")
    @Operation(
        summary = "Cambiar contraseña del usuario autenticado."
    )
    fun cambioPassword(
        @Valid @RequestBody request: PasswordRequest
    ) {

        service.cambiarPassword(request)
    }

    @GetMapping("/me/pruebas")
    @Operation(
        summary = "Obtener pruebas del usuario autenticado."
    )
    fun obtenerPruebas(): List<PruebaResponse> {

        return pruebaService.obtenerPruebasPropias()
            .map(PruebaDtoMapper::toResponse)
    }
}