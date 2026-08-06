package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.service.LugarService
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Municipio
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/departamentos")
@Tag(
    name = "Lugar",
    description = "Información de departamentos y municipios"
)
class LugarController (
    private val lugarService: LugarService
) {

    @GetMapping
    fun obtenerDepartamentos(): List<Departamento> {

        return lugarService.obtenerDepartamentos()
    }

    @GetMapping("/{id}/municipios")
    fun obtenerMunicipiosPorDepartamento(@PathVariable id: String): List<Municipio> {

        return lugarService.obtenerMunicipioPorDepartamento(id)
    }

}