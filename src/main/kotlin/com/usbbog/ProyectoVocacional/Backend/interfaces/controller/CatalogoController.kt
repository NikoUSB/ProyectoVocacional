package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.response.AreaProgramasResponse
import com.usbbog.proyectovocacional.backend.application.service.AreaService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Catálogo",
    description = "Catálogos públicos para el formulario de registro"
)
@RestController
@RequestMapping("/api/v1/catalogos")
class CatalogoController (
    private val areaService: AreaService
) {

    @GetMapping("/programas")
    fun obtenerCatalogoProgramas(): List<AreaProgramasResponse> {

        return areaService.obtenerCatalogoProgramas()

    }

}
