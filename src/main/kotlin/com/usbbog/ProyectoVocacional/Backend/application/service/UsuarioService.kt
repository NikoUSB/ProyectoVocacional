package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.PasswordRequest
import com.usbbog.proyectovocacional.backend.application.dto.request.usuario.UsuarioPerfilUpdateRequest
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
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
    private val usuarioAutenticadoService: UsuarioAutenticadoService,
    private val passwordService: PasswordService,
    private val logsService: LogsService

) {

    fun obtenerUsuarioAutenticado(): Usuario {
        return usuarioAutenticadoService.obtenerUsuarioAutenticado()
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario logueado no encontrado."
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

        val actualizado = usuario.copy(

            idPrograma = request.idPrograma,

            nombre = request.nombre,
            apellidos = request.apellidos,

            telefono = request.telefono,

            genero = request.genero,
            generoOtro = request.generoOtro,

            departamento = request.departamento,
            ciudad = request.municipio,

            semestre = request.semestre

        )

        logsService.generarLog(
            usuarioAlterado = usuario,
            descripcion = "ha actualizado su perfil.",
            estado = true
        )

        return repository.guardar(actualizado)
    }

    fun eliminarCuentaPropia() {

        val usuario = obtenerUsuarioAutenticado()

        if (usuario.idRol == 1L) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El rol ROOT no puede eliminar su propia cuenta."
            )
        }

        if (!usuario.activo) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El usuario ya se encuentra inactivo."
            )
        }

        val id = usuario.id
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Usuario no identificado."
            )

        repository.desactivar(id)

        logsService.generarLog(
            usuarioAlterado = usuario,
            descripcion = "ha desactivado su cuenta.",
            estado = true
        )
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

        logsService.generarLog(
            usuarioAlterado =  usuario,
            descripcion = "ha actualizado el rol del usuario ${usuario.nombreUsuario}.",
            estado = true
        )

        return repository.guardar(
            usuario.copy(
                idRol = idRol
            )
        )
    }

    fun cambiarPassword(request: PasswordRequest) {

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

        logsService.generarLog(
            usuarioAlterado = usuario,
            descripcion = "ha cambiado su contraseña",
            estado = true
        )

        repository.guardar(
            usuario.copy(
                contrasenaHash = passwordService.encode(request.passwordNueva)
            )
        )

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

    fun guardar(usuario:Usuario):Usuario{

        validarRol(usuario.idRol)
        validarPrograma(usuario.idPrograma)
        validarDocumento(usuario)
        validarCorreo(usuario)
        validarNombreUsuario(usuario)

        logsService.generarLog(
            usuarioAlterado = usuario,
            descripcion = "ha sido creado.",
            estado = true,
        )

        return repository.guardar(
            usuario.copy(
                contrasenaHash = passwordService.encode(usuario.contrasenaHash),
                activo = true,
                fechaCreacion =
                    LocalDateTime.now()
            )
        )

    }

    fun eliminar(id:Long){


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

        logsService.generarLog(
            usuarioAlterado = usuario,
            descripcion = "ha eliminado al usuario ${usuario.nombreUsuario}.",
            estado = true
        )

        repository.desactivar(id)

    }

    fun reactivar(id:Long){

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

        logsService.generarLog(
            usuarioAlterado = usuario,
            descripcion = "ha reactivado al usuario ${usuario.nombreUsuario}.",
            estado = true
        )

        repository.reactivar(
            id
        )

    }

    //Validaciones
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

        programaRepository.obtenerPorId(idPrograma)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El programa seleccionado no existe."
            )
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