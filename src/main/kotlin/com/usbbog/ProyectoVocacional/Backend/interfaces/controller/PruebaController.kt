package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.request.evaluacion.PruebaCreateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.ResultadoPruebaResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.PruebaResponse
import com.usbbog.proyectovocacional.backend.application.mapper.PruebaDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.PruebaService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(
    name = "Pruebas",
    description = "Presentación y consulta de pruebas vocacionales"
)
@RestController
@RequestMapping("/api/v1/pruebas")
class PruebaController(
    private val service: PruebaService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun presentar(
        @Valid @RequestBody request: PruebaCreateRequest
    ): ResultadoPruebaResponse {

        return service.presentar(request)

    }

    @GetMapping("/{id}")
    fun obtenerPorId(
        @PathVariable id: Long
    ): PruebaResponse {

        val prueba = service.obtenerPorId(id)

        return PruebaDtoMapper.toResponse(prueba)

    }

    @GetMapping("/{id}/resultado")
    fun obtenerResultado(
        @PathVariable id: Long
    ): ResultadoPruebaResponse {

        return service.obtenerResultado(id)

    }

    @GetMapping("/mis-pruebas")
    fun misPruebas(): List<PruebaResponse> {

        return service.obtenerMisPruebas()
            .map(PruebaDtoMapper::toResponse)

    }

}