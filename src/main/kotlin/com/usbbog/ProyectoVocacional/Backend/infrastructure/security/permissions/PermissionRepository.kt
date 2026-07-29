package com.usbbog.proyectovocacional.backend.infrastructure.security.permissions

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.ActividadEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

interface PermisoProyeccion {
    fun getMetodoHttp(): String
    fun getUrl(): String
}

interface PermissionRepository : Repository<ActividadEntity, Long> {

    @Query(
        value = """

            SELECT

                a.metodo_http AS metodoHttp,
                a.url         AS url

            FROM rol r

            INNER JOIN rol_actividad ra
                ON ra.id_rol = r.id

            INNER JOIN actividad a
                ON a.id = ra.id_actividad

            WHERE

                r.nombre_rol = :rol

                AND r.estado = true

                AND ra.estado = true

                AND a.estado = true

        """,
        nativeQuery = true
    )
    fun obtenerPermisosPorRol(

        @Param("rol")
        rol: String

    ): List<PermisoProyeccion>

}