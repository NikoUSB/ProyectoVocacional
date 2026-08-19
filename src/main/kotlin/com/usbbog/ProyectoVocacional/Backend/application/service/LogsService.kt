package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.response.LogsResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.UsuarioLogin
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.ActividadRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.LogsRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class LogsService(
    private val repository: LogsRepository,
    private val usuarioRepository: UsuarioRepository,
    private val actividadRepository: ActividadRepository,
    private val actividadService: ActividadService,
    private val usuarioAutenticadoService: UsuarioAutenticadoService
) {

    private val logger = LoggerFactory.getLogger(LogsService::class.java)

    fun obtenerTodos(): List<LogsResponse> {
        return repository.obtenerTodos().map { log ->
            val usuario = usuarioRepository.obtenerPorId(log.idUsuario)
            val actividad = actividadRepository.obtenerPorId(log.idActividad)
            LogsResponse(
                id = log.id,
                idUsuarioAlterado = log.idUsuario,
                nombreUsuario = usuario?.let { "${it.nombre} ${it.apellidos}".trim() },
                idActividad = log.idActividad,
                nombreActividad = actividad?.nombreActividad,
                descripcion = log.descripcionLog,
                fecha = log.fechaLog
            )
        }
    }

    fun obtenerPorUsuario(idUsuario: Long): List<LogsResponse> {
        return repository.obtenerPorUsuario(idUsuario).map { log ->
            val usuario = usuarioRepository.obtenerPorId(log.idUsuario)
            val actividad = actividadRepository.obtenerPorId(log.idActividad)
            LogsResponse(
                id = log.id,
                idUsuarioAlterado = log.idUsuario,
                nombreUsuario = usuario?.let { "${it.nombre} ${it.apellidos}".trim() },
                idActividad = log.idActividad,
                nombreActividad = actividad?.nombreActividad,
                descripcion = log.descripcionLog,
                fecha = log.fechaLog
            )
        }
    }

    private fun intentarObtenerActividad(): Long? {
        return try {
            val actividad = actividadService.obtenerActividadActual()
            actividad.id
        } catch (ex: ResponseStatusException) {
            logger.warn("No se encontró actividad para registrar log: ${ex.reason}")
            null
        }
    }

    fun generarLog(
        usuarioAlterado: Usuario?,
        descripcion: String,
        estado: Boolean
    ): Logs? {

        val actividadId = intentarObtenerActividad()
        if (actividadId == null) {
            logger.warn("No se registró log: no se encontró actividad para la solicitud actual.")
            return null
        }

        val usuario = usuarioAutenticadoService.obtenerUsuarioAutenticado()

        val log = Logs(
            id = null,
            idUsuario = usuario?.id ?: usuarioAlterado?.id
            ?: throw IllegalStateException("No se pudo obtener el id del usuario"),
            idUsuarioAlterado = usuarioAlterado?.id,
            idActividad = actividadId,
            descripcionLog = descripcion,
            fechaLog = LocalDateTime.now(),
            estado = estado
        )

        return repository.guardar(log)
    }

    fun generarLogLogin(
        usuario: UsuarioLogin?,
        usernameIntentado: String,
        descripcion: String,
        estado: Boolean
    ): Logs? {

        val actividadId = intentarObtenerActividad()
        if (actividadId == null) {
            logger.warn("No se registró log de login: no se encontró actividad para la solicitud actual.")
            return null
        }

        val log = Logs(
            id = null,
            idUsuario = usuario?.id ?: 0,
            idUsuarioAlterado = null,
            idActividad = actividadId,
            descripcionLog = descripcion,
            fechaLog = LocalDateTime.now(),
            estado = estado
        )

        return repository.guardar(log)
    }

    fun generarLogNoAutenticado(
        idUsuario: Long,
        usuarioAlterado: Usuario?,
        descripcion: String,
        estado: Boolean
    ): Logs? {

        val actividadId = intentarObtenerActividad()
        if (actividadId == null) {
            logger.warn("No se registró log (no autenticado): no se encontró actividad para la solicitud actual.")
            return null
        }

        val log = Logs(
            id = null,
            idUsuario = idUsuario,
            idUsuarioAlterado = usuarioAlterado?.id,
            idActividad = actividadId,
            descripcionLog = descripcion,
            fechaLog = LocalDateTime.now(),
            estado = estado
        )

        return repository.guardar(log)
    }

}
