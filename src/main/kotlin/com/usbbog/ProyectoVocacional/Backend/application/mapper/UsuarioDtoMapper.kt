package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioCreateRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioPerfilUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.UsuarioResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario

object UsuarioDtoMapper {


    fun toDomain(request: UsuarioCreateRequest): Usuario {

        return Usuario(

            id = null,

            idRol = 3,
            idPrograma = request.idPrograma,

            nombre = request.nombre,
            apellidos = request.apellidos,
            documento = request.documento,
            correo = request.correo,
            nombreUsuario = request.nombreUsuario,
            telefono = request.telefono,
            fechaNacimiento = request.fechaNacimiento,
            genero = request.genero,
            generoOtro = request.generoOtro,
            departamento = request.departamento,
            ciudad = request.municipio,
            semestre = request.semestre,

            //temporalmente

            contrasenaHash = request.contrasena,

            activo = true,

            fechaCreacion = null
            //fechaActualizacion = null

        )

    }

    fun toResponse(usuario:Usuario): UsuarioResponse{

        return UsuarioResponse(

            id = usuario.id,
            idRol = usuario.idRol,
            nombreUsuario = usuario.nombreUsuario,
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            documento = usuario.documento,
            correo = usuario.correo,
            telefono = usuario.telefono,
            fechaNacimiento = usuario.fechaNacimiento,
            genero = usuario.genero,
            generoOtro = usuario.generoOtro,
            departamento = usuario.departamento,
            municipio = usuario.ciudad,
            idPrograma = usuario.idPrograma,
            semestre = usuario.semestre,
            estado = usuario.activo,
            fechaCreacion = usuario.fechaCreacion
            //fechaActualizacion = usuario.fechaActualizacion

        )

    }

}