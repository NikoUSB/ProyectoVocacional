package com.usbbog.proyectovocacional.backend.application.mapper

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioCreateRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioPerfilUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.usuario.UsuarioPerfilResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.usuario.UsuarioResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.LugarRepository
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import org.springframework.stereotype.Component

@Component
class UsuarioDtoMapper (
    private val programaRepository: ProgramaRepository,
    private val lugarRepository: LugarRepository,
    private val rolRepository: RolRepository
) {


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
            municipio = request.municipio,
            semestre = request.semestre,

            //temporalmente

            contrasenaHash = request.contrasena,

            activo = true,

            fechaCreacion = null
            //fechaActualizacion = null

        )

    }

    fun toResponse(usuario:Usuario): UsuarioResponse{

        val programa = usuario.idPrograma?.let {
            programaRepository.obtenerPorId(it)?.nombrePrograma
        }

        val departamento = usuario.departamento?.let {
            lugarRepository.obtenerDepartamentoPorId(it)?.idDepartamento
        }

        val municipio = if (
            usuario.departamento != null &&
            usuario.municipio != null
        ) {
            lugarRepository
                .obtenerMunicipiosPorDepartamento(usuario.departamento)
                .find { it.idMunicipio == usuario.municipio }
                ?.nombreMunicipio
        } else {
            null
        }

        val tipoUsuario = when {
            usuario.idPrograma != null && usuario.semestre != null -> "Estudiante"
            usuario.idPrograma != null -> "Inscrito"
            else -> "Externo"
        }

        return UsuarioResponse(

            id = usuario.id,
            Rol = rolRepository.obtenerPorId(usuario.idRol)?.nombreRol ?: "Sin Rol Asignado!",
            tipoUsuario = tipoUsuario,
            nombreUsuario = usuario.nombreUsuario,
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            documento = usuario.documento,
            correo = usuario.correo,
            telefono = usuario.telefono,
            fechaNacimiento = usuario.fechaNacimiento,
            genero = usuario.genero,
            generoOtro = usuario.generoOtro,
            departamento = departamento,
            municipio = municipio,
            programa = programa,
            semestre = usuario.semestre,
            estado = usuario.activo,
            fechaCreacion = usuario.fechaCreacion
            //fechaActualizacion = usuario.fechaActualizacion

        )

    }

    fun toPerfilResponse(usuario: Usuario): UsuarioPerfilResponse {

        val programa = usuario.idPrograma?.let {
            programaRepository.obtenerPorId(it)
        }

        val departamento = usuario.departamento?.let {
            lugarRepository.obtenerDepartamentoPorId(it)
        }

        val municipio = if (
            usuario.departamento != null &&
            usuario.municipio != null
        ) {
            lugarRepository
                .obtenerMunicipiosPorDepartamento(usuario.departamento)
                .find { it.idMunicipio == usuario.municipio }
        } else {
            null
        }

        return UsuarioPerfilResponse(
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            telefono = usuario.telefono,
            genero = usuario.genero,
            generoOtro = usuario.generoOtro,

            departamento = departamento?.nombreDepartamento,
            municipio = municipio?.nombreMunicipio,
            programa = programa?.nombrePrograma,
            semestre = usuario.semestre
        )
    }
}