package com.usbbog.proyectovocacional.backend.interfaces.controller

import com.usbbog.proyectovocacional.backend.application.dto.response.DashboardResponse
import com.usbbog.proyectovocacional.backend.application.service.DashboardService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Dashboard",
    description = "Indicadores del panel administrativo"
)
@RestController
@RequestMapping("/api/v1/dashboard")
class DashboardController(
    private val service: DashboardService
) {

    @GetMapping
    fun obtenerDashboard(): DashboardResponse {

        return service.obtenerDashboard()

    }

}
