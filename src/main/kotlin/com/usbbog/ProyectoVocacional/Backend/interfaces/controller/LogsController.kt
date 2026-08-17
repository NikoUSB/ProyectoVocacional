package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.response.LogsResponse
import com.usbbog.proyectovocacional.backend.application.service.LogsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Logs",
    description = "Consulta de logs del sistema (solo ROOT)"
)
@RestController
@RequestMapping("/api/v1/logs")
class LogsController(
    private val logsService: LogsService
) {

    @PreAuthorize("hasRole('ROOT')")
    @Operation(
        summary = "Obtener todos los logs del sistema."
    )
    @GetMapping
    fun obtenerTodos(): List<LogsResponse> {
        return logsService.obtenerTodos()
    }

    @PreAuthorize("hasRole('ROOT')")
    @Operation(
        summary = "Obtener logs de un usuario específico."
    )
    @GetMapping("/usuario/{id}")
    fun obtenerPorUsuario(
        @PathVariable id: Long
    ): List<LogsResponse> {
        return logsService.obtenerPorUsuario(id)
    }

}
