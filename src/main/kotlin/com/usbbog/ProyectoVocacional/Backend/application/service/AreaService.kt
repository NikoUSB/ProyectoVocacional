package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.response.AreaProgramasResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.ProgramaCatalogoResponse
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.AreaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.config.AppProperties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@Service
class AreaService (
    private val repository: AreaRepository,
    private val logsService: LogsService,
    private val appProperties: AppProperties
) {
    fun obtenerTodos(): List<Area> {

        val areas = repository.obtenerTodos()

        if (areas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron áreas."
            )
        }

        return areas
    }

    fun obtenerTodosIncluyendoInactivos(): List<Area> {
        return repository.obtenerTodosIncluyendoInactivos()
    }

    fun obtenerPorId(id: Long): Area {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (!area.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El área se encuentra inactiva."
            )
        }

        return area
    }

    fun guardar(area: Area): Area {

        if (area.id != null) {
            val areaExistente = repository.obtenerPorId(area.id)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Área no encontrada."
                )

            if (!areaExistente.activo) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede modificar un área inactiva."
                )
            }
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha actualizado el área ${area.nombreArea}.",
            estado = true
        )

        return repository.guardar(area)
    }

    fun eliminar(id: Long) {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (!area.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El área ya se encuentra inactiva."
            )
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha eliminado el área ${area.nombreArea}.",
            estado = true
        )

        repository.eliminar(id)
    }

    fun reactivar(id: Long) {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (area.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El área ya se encuentra activa."
            )
        }

        logsService.generarLog(
            usuarioAlterado = null,
            descripcion = "ha reactivado el área ${area.nombreArea}.",
            estado = true
        )

        repository.reactivar(id)
    }

    fun obtenerProgramasPorArea(id: Long): List<Programa> {

        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (!area.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El área se encuentra inactiva."
            )
        }

        val programas = repository.obtenerProgramasPorArea(id)

        if (programas.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron programas para el área ${area.nombreArea}."
            )
        }

        return programas
    }

    fun obtenerCatalogoProgramas(): List<AreaProgramasResponse> {

        val areas = repository.obtenerTodos()

        return areas.mapNotNull { area ->
            val id = area.id
                ?: return@mapNotNull null

            val programas = repository.obtenerProgramasPorArea(id)

            if (programas.isEmpty()) {
                return@mapNotNull null
            }

            AreaProgramasResponse(
                id = id,
                nombreArea = area.nombreArea,
                programas = programas.mapNotNull { programa ->
                    val idPrograma = programa.id
                        ?: return@mapNotNull null

                    ProgramaCatalogoResponse(
                        id = idPrograma,
                        nombrePrograma = programa.nombrePrograma,
                        urlPrograma = programa.urlPrograma
                    )
                }
            )
        }
    }

    fun guardarPacho(id: Long, file: MultipartFile): String {
        val area = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Área con id $id no encontrada."
            )

        if (file.isEmpty) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El archivo no puede estar vacío."
            )
        }

        val allowedTypes = setOf("image/jpeg", "image/png", "image/webp")
        if (file.contentType !in allowedTypes) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Solo se permiten archivos JPEG, PNG o WEBP."
            )
        }

        val uploadDir = Paths.get(appProperties.uploadDir)
        Files.createDirectories(uploadDir)

        val extension = file.originalFilename?.substringAfterLast('.', "jpg") ?: "jpg"
        val filename = "pacho-${id}-${UUID.randomUUID()}.$extension"

        val targetPath = uploadDir.resolve(filename)
        file.bytes.let { Files.write(targetPath, it) }

        val updatedArea = area.copy(pachoPath = filename)
        repository.guardar(updatedArea)

        return filename
    }

}
