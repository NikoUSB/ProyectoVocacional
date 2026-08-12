package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.response.LogsResponse
import com.usbbog.proyectovocacional.backend.application.mapper.LogsDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.LogsService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
@Tag(
    name = "Logs",
    description = "Logs del sistema"
)
class LogsController (
    val service: LogsService,
    private val logsDtoMapper: LogsDtoMapper
) {

    @PreAuthorize("hasRole('ROOT')")
    @GetMapping("/logs")
    fun obtenerDepartamentos(): List<LogsResponse> {

        return service.obtenerLogs()
            .map { logsDtoMapper.toResponse(it) }

    }

}