package com.usbbog.proyectovocacional.backend.application.service

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

    fun actualizarPerfil(id: Long, request: UsuarioPerfilUpdateRequest): Usuario {

        val usuario = obtenerUsuarioAutenticado()

        if (usuario.id != id) {

            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "No puede modificar el perfil de otro usuario."
            )

        }

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

        return repository.guardar(actualizado)
    }

    fun actualizarRol(
        id: Long,
        idRol: Long
    ): Usuario {

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

        return repository.guardar(
            usuario.copy(
                idRol = idRol
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


    fun guardar(
        usuario:Usuario
    ):Usuario{

        validarRol(usuario.idRol)
        validarPrograma(usuario.idPrograma)
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


    fun eliminar(
        id:Long
    ){


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

    }

    fun reactivar(

        id:Long

    ){

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

    }


    private fun validarRol(
        idRol: Long
    ) {

        rolRepository.obtenerPorId(idRol)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El rol seleccionado no existe."
            )
    }

    private fun validarPrograma(
        idPrograma: Long?
    ) {

        if (idPrograma == null) {
            return
        }

        programaRepository.obtenerPorId(idPrograma)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El programa seleccionado no existe."
            )
    }



    private fun validarDocumento(
        usuario:Usuario
    ){

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



    private fun validarCorreo(

        usuario:Usuario

    ){

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