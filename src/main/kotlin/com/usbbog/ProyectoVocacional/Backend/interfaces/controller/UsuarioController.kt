package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.PasswordRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioPerfilUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioRolUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.MensajeResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.PruebaResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.UsuarioResponse
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

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(
    name = "Usuarios",
    description = "Gestión de usuarios del sistema."
)
class UsuarioController(

    private val service: UsuarioService,
    private val pruebaService: PruebaService

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

    @Operation(
        summary = "Actualizar datos del usuario autenticado."
    )
    @PutMapping("/me/perfil")
    fun actualizarPerfil(
        @Valid @RequestBody request: UsuarioPerfilUpdateRequest
    ): UsuarioResponse {

        return UsuarioDtoMapper.toResponse(
            service.actualizarPerfil(request)
        )
    }

    @GetMapping("/me")
    @Operation(
        summary = "Obtener el perfil del usuario autenticado."
    )
    fun obtenerPerfilAutenticado(): UsuarioResponse {

        return UsuarioDtoMapper.toResponse(
            service.obtenerUsuarioAutenticado()
        )

    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Eliminar la cuenta del usuario autenticado."
    )
    fun eliminarCuentaPropia() {

        service.eliminarCuentaPropia()

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

    @PreAuthorize("hasRole('ROOT')")
    @PatchMapping("/{id}/rol")
    @Operation(
        summary = "Actualizar rol de un usuario."
    )
    fun actualizarRol(
        @PathVariable
        id: Long,
        @Valid
        @RequestBody
        request: UsuarioRolUpdateRequest
    ): UsuarioResponse {
        return UsuarioDtoMapper.toResponse( service.actualizarRol( id, request.idRol ) )
    }

    @PreAuthorize("hasRole('ROOT') or hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}/pruebas")
    fun obtenerPruebasDeUsuario(
        @PathVariable id: Long
    ): List<PruebaResponse> {

        return pruebaService.obtenerPruebasDeUsuario(id)
            .map(PruebaDtoMapper::toResponse)

    }

    @Operation(
        summary = "Cambiar la contraseña del usuario autenticado."
    )
    @PostMapping("/me/cambiar-contrasena")
    fun cambiarContrasena(
        @Valid @RequestBody request: PasswordRequest
    ): MensajeResponse {

        return service.cambiarContrasena(request)

    }

    @PreAuthorize("hasRole('ROOT')")
    @Operation(
        summary = "Restablecer la contraseña de un usuario (solo ROOT)."
    )
    @PostMapping("/{id}/restablecer-contrasena")
    fun restablecerContrasenaDeAdmin(
        @PathVariable id: Long
    ): MensajeResponse {

        return service.restablecerContrasenaDeAdmin(id)

    }

}