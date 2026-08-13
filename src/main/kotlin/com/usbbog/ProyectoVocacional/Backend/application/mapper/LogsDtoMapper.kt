package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.response.LogsResponse
import com.usbbog.proyectovocacional.backend.application.service.UsuarioService
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Logs
import org.springframework.stereotype.Component

@Component
class LogsDtoMapper(
    private val usuarioService: UsuarioService
) {

    fun toResponse(log: Logs): LogsResponse {

        val usuario = usuarioService.obtenerPorId(log.idUsuario)

        val mensaje = if (log.estado) {
            "El usuario ${usuario.nombreUsuario} ${log.descripcionLog}"
        } else {
            "Error: El usuario ${usuario.nombreUsuario} ${log.descripcionLog}"
        }

        return LogsResponse(
            fecha = log.fechaLog,
            log = mensaje,
            estado = log.estado
        )
    }
}