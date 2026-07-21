package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Rol
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.RolEntity

object RolMapper {

    fun toDomain(entity: RolEntity): Rol {

        return Rol(
            id = entity.id,
            nombreRol = entity.nombreRol,
            activo = entity.activo
        )

    }

    fun toEntity(domain: Rol): RolEntity {

        return RolEntity(
            id = domain.id,
            nombreRol = domain.nombreRol,
            activo = domain.activo
        )

    }

}