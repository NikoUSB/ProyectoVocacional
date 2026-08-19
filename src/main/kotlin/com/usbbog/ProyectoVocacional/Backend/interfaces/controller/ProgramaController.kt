package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.ProgramaRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.PreguntaResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.ProgramaResponse
import com.usbbog.proyectovocacional.backend.application.mapper.PreguntaDtoMapper
import com.usbbog.proyectovocacional.backend.application.mapper.ProgramaDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.ProgramaService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(
    name = "Programas",
    description = "Gestion del catálogo de programas"
)
@RestController
@RequestMapping("/api/v1/programas")
class ProgramaController (
    private val programaService: ProgramaService
) {

    @GetMapping
    fun obtenerTodos(): List<ProgramaResponse> {

        return programaService.obtenerTodosIncluyendoInactivos()
            .map(ProgramaDtoMapper::toResponse)

    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ProgramaResponse {

        val area = programaService.obtenerPorId(id)

        return ProgramaDtoMapper.toResponse(area)

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crear(
        @RequestBody request: ProgramaRequest
    ): ProgramaResponse {

        val area = ProgramaDtoMapper.toDomain(request)

        val creado = programaService.guardar(area)

        return ProgramaDtoMapper.toResponse(creado)

    }


    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Long,
        @RequestBody request: ProgramaRequest
    ): ProgramaResponse {

        val area = ProgramaDtoMapper.toDomain(id, request)

        val actualizado = programaService.guardar(area)

        return ProgramaDtoMapper.toResponse(actualizado)

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun eliminar(
        @PathVariable id: Long
    ) {

        programaService.eliminar(id)

    }

    @PatchMapping("/{id}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun reactivar(
        @PathVariable id: Long
    ) {
        programaService.reactivar(id)
    }

    @GetMapping("/{id}/preguntas")
    fun obtenerPreguntasPorPrograma(
        @PathVariable id: Long
    ): List<PreguntaResponse> {

        val programas = programaService.obtenerPreguntasPorPrograma(id)
            .map(PreguntaDtoMapper::toResponse)

        return programas
    }

}