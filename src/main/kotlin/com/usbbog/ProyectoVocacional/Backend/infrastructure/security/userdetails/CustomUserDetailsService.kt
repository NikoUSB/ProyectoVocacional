package com.usbbog.proyectovocacional.backend.infrastructure.security.userdetails

import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(

    private val usuarioRepository: UsuarioRepository,

    private val rolRepository: RolRepository

) : UserDetailsService {


    override fun loadUserByUsername(

        username: String

    ): UserDetails {


        val usuario =

            usuarioRepository
                .obtenerPorNombreUsuario(
                    username
                )

                ?: throw UsernameNotFoundException(

                    "Usuario no encontrado."

                )


        val rol =

            rolRepository
                .obtenerPorId(
                    usuario.idRol
                )

                ?: throw UsernameNotFoundException(

                    "Rol no encontrado."

                )


        return CustomUserDetails(

            usuario.nombreUsuario!!,

            usuario.contrasenaHash,

            rol.nombreRol,

            usuario.activo

        )

    }

}