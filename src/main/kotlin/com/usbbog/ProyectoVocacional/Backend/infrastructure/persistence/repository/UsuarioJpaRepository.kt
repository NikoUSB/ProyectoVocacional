package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.projection.UsuarioLoginProjection
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.UsuarioEntity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

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

    @Query(
        value = """
        SELECT
            u.id              AS id,
            u.nombre_usuario  AS nombreUsuario,
            u.contrasena_hash AS contrasenaHash,
            u.estado          AS estado,
            r.nombre_rol      AS nombreRol
        FROM usuario u
        INNER JOIN rol r
            ON r.id = u.id_rol
        WHERE
            u.estado = true
        AND
        (
            u.nombre_usuario = :value
            OR
            u.correo = :value
        )
    """,
        nativeQuery = true
    )
    fun obtenerUsuarioConRol(

        @Param("value")

        value:String

    ): UsuarioLoginProjection?

}