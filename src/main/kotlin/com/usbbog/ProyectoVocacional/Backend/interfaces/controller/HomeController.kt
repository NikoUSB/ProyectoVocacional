package com.usbbog.proyectovocacional.backend.interfaces.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Home",
    description = "Información del aplicativo"
)
@RestController
class HomeController {


    @GetMapping("/")
    fun home(): Map<String, Any> {

        return mapOf(
            "application" to "Proyecto Vocacional API",
            "status" to "UP",
            "message" to "API funcionando correctamente",
            "version" to "1.0.0",
            "documentation" to "/swagger"
        )
    }


}