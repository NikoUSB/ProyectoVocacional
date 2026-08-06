package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.response.ActividadResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.AreaResponse
import com.usbbog.proyectovocacional.backend.application.mapper.ActividadDtoMapper
import com.usbbog.proyectovocacional.backend.application.mapper.AreaDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.ActividadService
import com.usbbog.proyectovocacional.backend.application.service.RolActividadService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Actividad",
    description = "Gestion de las actividades"
)
@RestController
@RequestMapping("/api/v1/actividades")
class ActividadController (

    private val actividadService: ActividadService,
    //private val rolActivisadService: RolActividadService

) {

    @GetMapping
    fun obtenerTodos(): List<ActividadResponse> {

        return actividadService.obtenerTodos()
            .map(ActividadDtoMapper::toResponse)
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ActividadResponse {

        val actividad = actividadService.obtenerPorId(id)

        return ActividadDtoMapper.toResponse(actividad)

    }


}