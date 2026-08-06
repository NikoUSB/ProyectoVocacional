package com.usbbog.proyectovocacional.backend.interfaces.controller


import com.usbbog.proyectovocacional.backend.application.dto.request.PreguntaRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.PreguntaResponse
import com.usbbog.proyectovocacional.backend.application.mapper.PreguntaDtoMapper
import com.usbbog.proyectovocacional.backend.application.service.PreguntaService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
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

@Tag(
    name = "Preguntas",
    description = "Gestión del catálogo de preguntas"
)
@RestController
@RequestMapping("/api/v1/preguntas")
class PreguntaController(
    private val preguntaService: PreguntaService
) {

    @GetMapping
    fun obtenerPreguntas() : List<PreguntaResponse> {
        val preguntas = preguntaService.obtenerTodos()
            .map(PreguntaDtoMapper :: toResponse)

        return preguntas
    }

    @GetMapping("/{id}")
    fun obtenerPorId(
        @PathVariable id: Long
    ): PreguntaResponse {

        val pregunta = preguntaService.obtenerPorId(id)
        return PreguntaDtoMapper.toResponse(pregunta)

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crear(
        @RequestBody request: PreguntaRequest
    ): PreguntaResponse {

        val pregunta = PreguntaDtoMapper.toDomain(request)

        val creado = preguntaService.guardar(pregunta)

        return PreguntaDtoMapper.toResponse(creado)

    }


    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Long,
        @RequestBody request: PreguntaRequest
    ): PreguntaResponse {

        val pregunta = PreguntaDtoMapper.toDomain(id, request)

        val actualizado = preguntaService.guardar(pregunta)

        return PreguntaDtoMapper.toResponse(actualizado)

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun eliminar(
        @PathVariable id: Long
    ) {

        preguntaService.eliminar(id)

    }

    @PatchMapping("/{id}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun reactivar(
        @PathVariable id: Long
    ) {
        preguntaService.reactivar(id)
    }

}