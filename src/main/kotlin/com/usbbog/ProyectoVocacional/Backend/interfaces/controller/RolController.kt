package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.Rol.RolRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.RolResponse
import com.usbbog.proyectovocacional.backend.application.DTOmapper.RolDtoMapper
import com.usbbog.proyectovocacional.backend.application.dto.request.Rol.RolActividadRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.ActividadResponse
import com.usbbog.proyectovocacional.backend.application.mapper.ActividadDtoMapper
import com.usbbog.proyectovocacional.backend.application.mapper.ActividadDtoMapper.toResponse
import com.usbbog.proyectovocacional.backend.application.service.ActividadService
import com.usbbog.proyectovocacional.backend.application.service.RolActividadService
import com.usbbog.proyectovocacional.backend.application.service.RolService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(
    name = "Roles",
    description = "Gestión de roles y permisos del sistema"
)
@RestController
@RequestMapping("/api/v1/roles")
class RolController(

    private val rolService: RolService,
    private val actividadService: ActividadService,
    private val rolActividadService: RolActividadService

) {

    @PreAuthorize("hasRole('ROOT')")
    @GetMapping
    fun obtenerTodos(): List<RolResponse> {

        return rolService.obtenerTodos()
            .map(RolDtoMapper::toResponse)

    }

    @PreAuthorize("hasRole('ROOT')")
    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): RolResponse {
        println("ENTRÓ AL CONTROLLER DE ROLES")
        val rol = rolService.obtenerPorId(id)

        return RolDtoMapper.toResponse(rol)

    }

    @PreAuthorize("hasRole('ROOT')")
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


    @PreAuthorize("hasRole('ROOT')")
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

    @PreAuthorize("hasRole('ROOT')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun eliminar(
        @PathVariable id: Long
    ) {

        rolService.eliminar(id)

    }

    @PreAuthorize("hasRole('ROOT')")
    @PatchMapping("/{id}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun reactivar(
        @PathVariable id: Long
    ) {
        rolService.reactivar(id)
    }

    @GetMapping("/{id}/actividades")
    fun obtenerActividadesPorRol(@PathVariable id: Long): List<ActividadResponse> {

        return actividadService.obtenerActividadesPorRolId(id)
            .map(ActividadDtoMapper::toResponse)
    }

    @PreAuthorize("hasRole('ROOT')")
    @PutMapping("/{id}/actividades")
    fun actualizarActividades(
        @PathVariable id: Long,
        @RequestBody request: RolActividadRequest
    ) {
        rolActividadService.actualizarActividades(
            id,
            request.actividades
        )
    }

}