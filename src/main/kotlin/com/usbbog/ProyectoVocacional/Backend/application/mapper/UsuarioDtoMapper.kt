package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioCreateRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.UsuarioResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import java.time.LocalDate

object UsuarioDtoMapper {


    fun toDomain(request: UsuarioCreateRequest): Usuario {

        return Usuario(

            id = null,

            idRol = request.idRol,
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
            ciudad = request.ciudad,
            semestre = request.semestre,

            //temporalmente

            contrasenaHash = request.contrasena,

            activo = true,

            fechaCreacion = null
            //fechaActualizacion = null

        )

    }


    fun toDomain(id:Long, request: UsuarioUpdateRequest):Usuario{

        return Usuario(

            id = id,
            idRol = request.idRol,

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
            ciudad = request.ciudad,
            idPrograma = request.idPrograma,
            semestre = request.semestre,

            //NO se modifica aquí
            contrasenaHash = "",

            activo = request.estado,

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
            ciudad = usuario.ciudad,
            idPrograma = usuario.idPrograma,
            semestre = usuario.semestre,
            estado = usuario.activo,
            fechaCreacion = usuario.fechaCreacion
            //fechaActualizacion = usuario.fechaActualizacion

        )

    }

}