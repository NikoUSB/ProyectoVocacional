package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.PasswordRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioPerfilUpdateRequest
import com.usbbog.proyectovocacional.backend.application.dto.response.usuario.UsuarioPerfilResponse
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.LugarRepository
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.PasswordService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class UsuarioService(

    private val repository: UsuarioRepository,

    private val rolRepository: RolRepository,

    private val programaRepository: ProgramaRepository,

    private val lugarRepository: LugarRepository,

    private val passwordService: PasswordService

) {

    fun obtenerUsuarioAutenticado(): Usuario {

        val authentication =
            SecurityContextHolder
                .getContext()
                .authentication
                ?: throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "No hay un usuario autenticado."
                )

        val username = authentication.name

        return repository.obtenerPorNombreUsuario(username)
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Usuario autenticado no encontrado."
            )
    }

    fun actualizarPerfil(request: UsuarioPerfilUpdateRequest): Usuario {

        val usuario = obtenerUsuarioAutenticado()

        if (!usuario.activo) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario inactivo."
            )
        }

        validarPrograma(request.idPrograma)
        validarUbicacion(request.departamento, request.municipio)

        val actualizado = usuario.copy(
            nombre = request.nombre ?: usuario.nombre,
            apellidos = request.apellidos ?: usuario.apellidos,

            telefono = request.telefono ?: usuario.telefono,
            genero = request.genero ?: usuario.genero,
            generoOtro = request.generoOtro ?: usuario.generoOtro,

            departamento = request.departamento ?: usuario.departamento,
            municipio = request.municipio ?: usuario.municipio,

            idPrograma = request.idPrograma ?: usuario.idPrograma,
            semestre = request.semestre ?: usuario.semestre
        )

        repository.guardar(actualizado)

        return actualizado
    }

    fun actualizarRol(id: Long, idRol: Long): Usuario {

        val usuario = repository.obtenerPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario no encontrado."
            )

        if (!usuario.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No se puede modificar el rol de un usuario inactivo."
            )
        }

        validarRol(idRol)

        return repository.guardar(usuario.copy(idRol = idRol))
    }

    fun cambiarPassword(request: PasswordRequest) : ResponseStatusException{

        val usuario = obtenerUsuarioAutenticado()

        if (!passwordService.matches(request.passwordActual, usuario.contrasenaHash)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La contraseña actual no es correcta."
            )
        }

        if (request.passwordActual == request.passwordNueva) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La nueva contraseña debe ser diferente a la actual."
            )
        }

        repository.guardar(
            usuario.copy(
                contrasenaHash = passwordService.encode(request.passwordNueva)
            )
        )

        return ResponseStatusException(HttpStatus.OK, "Se ha actualizado correctamente.")

    }

    fun obtenerTodos():List<Usuario>{

        val usuarios = repository.obtenerTodos()


        if(usuarios.isEmpty()){

            throw ResponseStatusException(

                HttpStatus.NOT_FOUND,

                "No se encontraron usuarios."

            )

        }


        return usuarios

    }

    fun obtenerPorId(id:Long):Usuario{

        val usuario = repository.obtenerPorId(id)

            ?: throw ResponseStatusException(

                HttpStatus.NOT_FOUND,

                "Usuario con id $id no encontrado."

            )

        if (!usuario.activo){
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario inactivo."
            )
        }

        return usuario

    }

    fun guardar(usuario:Usuario) : Usuario {

        validarRol(usuario.idRol)
        validarPrograma(usuario.idPrograma)
        validarUbicacion(usuario.departamento, usuario.municipio)
        validarDocumento(usuario)
        validarCorreo(usuario)
        validarNombreUsuario(usuario)

        return repository.guardar(
            usuario.copy(
                contrasenaHash = passwordService.encode(usuario.contrasenaHash),
                activo = true,
                fechaCreacion =
                    LocalDateTime.now()
            )
        )
    }

    fun eliminar(id:Long) : ResponseStatusException {


        val usuario =

            repository.obtenerPorId(id)

                ?: throw ResponseStatusException(

                    HttpStatus.NOT_FOUND,

                    "Usuario no encontrado."

                )


        if(!usuario.activo){

            throw ResponseStatusException(

                HttpStatus.BAD_REQUEST,

                "El usuario ya se encuentra inactivo."

            )

        }


        repository.desactivar(id)

        return ResponseStatusException(HttpStatus.OK, "Se ha desacrivado el usuario ${usuario.nombreUsuario}.")

    }

    fun eliminarPerfilPropio(): ResponseStatusException{

        val usuario = obtenerUsuarioAutenticado()

        if (!usuario.activo) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Tu cuenta ya se encuentra inactiva.")
        }

        repository.desactivar(usuario.id!!)

        return ResponseStatusException(HttpStatus.OK, "Se ha eliminado tu perfil.")
    }

    fun reactivar(id:Long): ResponseStatusException{

        val usuario = repository.obtenerPorId(id)

            ?: throw ResponseStatusException(

                HttpStatus.NOT_FOUND,

                "Usuario con id $id no encontrado."

            )


        if(usuario.activo){

            throw ResponseStatusException(

                HttpStatus.BAD_REQUEST,

                "El usuario ya se encuentra activo."

            )

        }


        repository.reactivar(
            id
        )

        return ResponseStatusException(HttpStatus.OK, "Se ha reactivado el usuario ${usuario.nombreUsuario}.")

    }

    //VALIDACIONES
    private fun validarRol(idRol: Long) {

        rolRepository.obtenerPorId(idRol)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El rol seleccionado no existe."
            )
    }

    private fun validarPrograma(idPrograma: Long?) {

        if (idPrograma == null) {
            return
        }


        val programa = programaRepository.obtenerPorId(idPrograma)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El programa seleccionado no existe."
            )

        if (programa.urlPrograma == null) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El programa seleccionado no hace parte del catalogo de la Universidad de San Buenaventura."
            )
        }
    }

    private fun validarUbicacion(idDepartamento: String?, idMunicipio: String?) {

        if (idDepartamento == null && idMunicipio == null) return

        if (idDepartamento == null || idMunicipio == null) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes indicar tanto el departamento como el municipio."
            )
        }

        lugarRepository.obtenerDepartamentoPorId(idDepartamento)
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El departamento indicado no existe."
            )

        val municipiosValidos = lugarRepository.obtenerMunicipiosPorDepartamento(idDepartamento)

        if (municipiosValidos.none { it.idMunicipio == idMunicipio }) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El municipio no pertenece al departamento indicado."
            )
        }
    }

    private fun validarDocumento(usuario:Usuario){

        if(

            repository.obtenerPorDocumento(

                usuario.documento

            ) != null

        ){

            throw ResponseStatusException(

                HttpStatus.BAD_REQUEST,

                "El documento ya se encuentra registrado."

            )

        }


    }

    private fun validarCorreo(usuario:Usuario){

        val usuarioEncontrado =

            repository.obtenerPorCorreo(

                usuario.correo

            )

        if(

            usuarioEncontrado != null &&

            usuarioEncontrado.id != usuario.id

        ){

            throw ResponseStatusException(

                HttpStatus.BAD_REQUEST,

                "El correo ya se encuentra registrado."

            )

        }

    }

    private fun validarNombreUsuario(usuario: Usuario) {

        val nombreUsuario = usuario.nombreUsuario
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El nombre de usuario es obligatorio."
            )

        if (repository.obtenerPorNombreUsuario(nombreUsuario) != null) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El nombre de usuario ya existe."
            )
        }
    }

}