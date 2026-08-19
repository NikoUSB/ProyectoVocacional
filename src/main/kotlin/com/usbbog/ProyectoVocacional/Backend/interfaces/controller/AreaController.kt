package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.AreaRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.AreaResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.ProgramaResponse
import com.usbbog.proyectovocacional.backend.application.mapper.AreaDtoMapper
import com.usbbog.proyectovocacional.backend.application.mapper.ProgramaDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.AreaService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(
    name = "Areas",
    description = "Gestion del catálogo de áreas"
)
@RestController
@RequestMapping("/api/v1/areas")
class AreaController (

    private val areaService: AreaService

)  {

    @GetMapping
    fun obtenerTodos(): List<AreaResponse> {

        return areaService.obtenerTodosIncluyendoInactivos()
            .map(AreaDtoMapper::toResponse)

    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): AreaResponse {

        val area = areaService.obtenerPorId(id)

        return AreaDtoMapper.toResponse(area)

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crear(
        @RequestBody request: AreaRequest
    ): AreaResponse {

        val area = AreaDtoMapper.toDomain(request)

        val creado = areaService.guardar(area)

        return AreaDtoMapper.toResponse(creado)

    }


    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Long,
        @RequestBody request: AreaRequest
    ): AreaResponse {

        val area = AreaDtoMapper.toDomain(id, request)

        val actualizado = areaService.guardar(area)

        return AreaDtoMapper.toResponse(actualizado)

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun eliminar(
        @PathVariable id: Long
    ) {

        areaService.eliminar(id)

    }

    @PatchMapping("/{id}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun reactivar(
        @PathVariable id: Long
    ) {
        areaService.reactivar(id)
    }

    @GetMapping("/{id}/programas")
    fun obtenerProgramasPorArea(
        @PathVariable id: Long
    ): List<ProgramaResponse> {

        val programas = areaService.obtenerProgramasPorArea(id)
            .map(ProgramaDtoMapper::toResponse)

        return programas
    }

    @PostMapping("/{id}/imagen-pacho", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirPacho(
        @PathVariable id: Long,
        @RequestBody file: MultipartFile
    ): AreaResponse {

        areaService.guardarPacho(id, file)

        val area = areaService.obtenerPorId(id)

        return AreaDtoMapper.toResponse(area)
    }


}