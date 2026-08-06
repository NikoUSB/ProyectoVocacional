package com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.UsuarioEntity

object UsuarioMapper {

    fun toDomain(
        entity: UsuarioEntity
    ): Usuario {

        return Usuario(

            id = entity.id,
            idRol = entity.idRol,
            nombreUsuario = entity.nombreUsuario,
            contrasenaHash = entity.contrasenaHash,
            documento = entity.documento,
            nombre = entity.nombre,
            apellidos = entity.apellidos,
            correo = entity.correo,
            telefono = entity.telefono,
            fechaNacimiento = entity.fechaNacimiento,
            genero = entity.genero,
            generoOtro = entity.generoOtro,
            departamento = entity.departamento,
            ciudad = entity.municipio,
            idPrograma = entity.idPrograma,
            semestre = entity.semestre,
            fechaCreacion = entity.fechaCreacion,
            activo = entity.activo
            //fechaActualizacion = entity.fechaActualizacion
        )

    }


    fun toEntity(
        domain: Usuario
    ): UsuarioEntity {

        return UsuarioEntity(

            id = domain.id,
            idRol = domain.idRol,
            nombreUsuario = domain.nombreUsuario,
            contrasenaHash = domain.contrasenaHash,
            documento = domain.documento,
            nombre = domain.nombre,
            apellidos = domain.apellidos,
            correo = domain.correo,
            telefono = domain.telefono,
            fechaNacimiento = domain.fechaNacimiento,
            genero = domain.genero,
            generoOtro = domain.generoOtro,
            departamento = domain.departamento,
            municipio = domain.ciudad,
            idPrograma = domain.idPrograma,
            semestre = domain.semestre,
            fechaCreacion = domain.fechaCreacion,
            activo = domain.activo
            //fechaActualizacion = domain.fechaActualizacion

        )

    }

}