package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioJpaRepository :
    JpaRepository<UsuarioEntity, Long> {

    fun findByActivoTrue(): List<UsuarioEntity>

    fun findByNombreUsuario(
        nombreUsuario: String
    ): UsuarioEntity?

    fun findByCorreo(
        correo: String
    ): UsuarioEntity?

    fun findByNombreUsuarioOrCorreo(

        nombreUsuario:String,

        correo:String

    ):UsuarioEntity?

    fun findByDocumento(
        documento: String
    ): UsuarioEntity?


}