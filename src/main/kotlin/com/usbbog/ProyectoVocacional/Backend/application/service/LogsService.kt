package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.response.LogsResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.UsuarioLogin
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.ActividadRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.LogsRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
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

    fun generarLog(
        usuarioAlterado: Usuario?,
        descripcion: String,
        estado: Boolean
    ): Logs {

        val usuario = usuarioAutenticadoService.obtenerUsuarioAutenticado()

        val actividad = actividadService.obtenerActividadActual()

        val log = Logs(
            id = null,
            idUsuario = usuario?.id ?: usuarioAlterado?.id
            ?: throw IllegalStateException("No se pudo obtener el id del usuario"),
            idUsuarioAlterado = usuarioAlterado?.id,
            idActividad = actividad.id!!,
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
    ): Logs {

        val actividad = actividadService.obtenerActividadActual()

        val log = Logs(
            id = null,
            idUsuario = usuario?.id ?: 0,
            idUsuarioAlterado = null,
            idActividad = actividad.id!!,
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
    ): Logs {

        val actividad = actividadService.obtenerActividadActual()

        val log = Logs(
            id = null,
            idUsuario = idUsuario,
            idUsuarioAlterado = usuarioAlterado?.id,
            idActividad = actividad.id!!,
            descripcionLog = descripcion,
            fechaLog = LocalDateTime.now(),
            estado = estado
        )

        return repository.guardar(log)
    }

}
