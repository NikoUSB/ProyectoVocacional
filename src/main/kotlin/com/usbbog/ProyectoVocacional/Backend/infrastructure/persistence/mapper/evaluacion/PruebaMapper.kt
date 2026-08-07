package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.evaluacion

import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Prueba
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion.PruebaEntity
import java.time.LocalDateTime

object PruebaMapper {

    fun toDomain(entity: PruebaEntity) = Prueba(
        id = entity.id,
        idUsuario = entity.idUsuario,
        fecha = entity.fecha,
        tiempoInvertido = entity.tiempoInvertido,
        versionPrueba = entity.versionPrueba,
        satisfaccion = entity.satisfaccion,
        activo = entity.activo
    )

    fun toEntity(domain: Prueba) = PruebaEntity(
        id = domain.id,
        idUsuario = domain.idUsuario,
        fecha = domain.fecha ?: LocalDateTime.now(),
        tiempoInvertido = domain.tiempoInvertido,
        versionPrueba = domain.versionPrueba,
        satisfaccion = domain.satisfaccion,
        activo = domain.activo
    )
}