package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.UsuarioLogin
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad.UsuarioMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.UsuarioJpaRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.projection.UsuarioLoginProjection
import org.springframework.stereotype.Repository

@Repository
class UsuarioRepositoryImpl(

    private val jpaRepository:
    UsuarioJpaRepository

):UsuarioRepository{
    override fun obtenerTodos(): List<Usuario> {
        return jpaRepository.findByActivoTrue()
            .map(UsuarioMapper::toDomain)
    }

    override fun obtenerPorId(id: Long): Usuario? {
        return jpaRepository.findById(id)
            .map(UsuarioMapper ::toDomain)
            .orElse(null)
    }


    override fun obtenerPorCorreo(
        correo:String
    ):Usuario?{

        return jpaRepository
            .findByCorreo(correo)
            ?.let(UsuarioMapper::toDomain)

    }

    override fun obtenerPorDocumento(documento: String): Usuario? {
        return jpaRepository
            .findByDocumento(documento)
            ?.let(UsuarioMapper::toDomain)
    }


    override fun obtenerPorNombreUsuario(
        nombreUsuario:String
    ):Usuario?{

        return jpaRepository
            .findByNombreUsuario(nombreUsuario)
            ?.let(UsuarioMapper::toDomain)

    }

    override fun obtenerPorCorreoONombreUsuario(

        value:String

    ):Usuario?{


        return jpaRepository

            .findByNombreUsuarioOrCorreo(

                value,
                value

            )

            ?.let(
                UsuarioMapper::toDomain
            )


    }


    override fun guardar(usuario: Usuario): Usuario {
        val entity = UsuarioMapper.toEntity(usuario)

        return UsuarioMapper.toDomain(
            jpaRepository.save(entity)
        )
    }

    override fun desactivar(id: Long) {
        val entity = jpaRepository.findById(id)
            .orElseThrow()

        entity.activo = false

        jpaRepository.save(entity)
    }

    override fun reactivar(id: Long) {
        val entity = jpaRepository.findById(id)
            .orElseThrow()

        entity.activo = true

        jpaRepository.save(entity)
    }

    override fun obtenerUsuarioConRol(

        value: String

    ): UsuarioLogin? {

        return jpaRepository
            .obtenerUsuarioConRol(value)
            ?.let {

                UsuarioLogin(

                    id = it.id,

                    nombreUsuario = it.nombreUsuario,

                    contrasenaHash = it.contrasenaHash,

                    rol = it.nombreRol,

                    activo = it.estado

                )

            }

    }

}