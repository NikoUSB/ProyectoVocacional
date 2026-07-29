package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import com.usbbog.proyectovocacional.backend.infrastructure.security.password.PasswordService
import org.springframework.http.HttpStatus
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

        /*UPDATE*/
        if(usuario.id != null){
            val usuarioActual =
                repository.obtenerPorId(usuario.id)
                    ?: throw ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado."
                    )


            return repository.guardar(
                usuario.copy(
                    activo = true,
                    fechaCreacion = LocalDateTime.now()
                )

            )
        }



        /*
            CREATE
         */

        validarRol(usuario)
        validarPrograma(usuario)
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

        val usuario =

            obtenerPorId(id)


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
        usuario:Usuario
    ){

        rolRepository.obtenerPorId(
            usuario.idRol
        )

            ?: throw ResponseStatusException(

                HttpStatus.NOT_FOUND,

                "El rol seleccionado no existe."

            )

    }



    private fun validarPrograma(
        usuario:Usuario
    ){

        if(usuario.idPrograma == null){

            return

        }


        programaRepository.obtenerPorId(

            usuario.idPrograma

        )

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


    private fun validarNombreUsuario(
        usuario:Usuario
    ){

        if(

            repository.obtenerPorNombreUsuario(

                usuario.nombreUsuario!!

            ) != null

        ){

            throw ResponseStatusException(

                HttpStatus.BAD_REQUEST,

                "El nombre de usuario ya existe."

            )

        }


    }

}