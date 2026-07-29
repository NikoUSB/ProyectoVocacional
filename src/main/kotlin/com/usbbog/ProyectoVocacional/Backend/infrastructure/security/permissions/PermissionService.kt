package com.usbbog.proyectovocacional.backend.infrastructure.security.permissions

import org.springframework.stereotype.Service
import org.springframework.util.AntPathMatcher

@Service
class PermissionService(

    private val permissionRepository: PermissionRepository

) {

    // AntPathMatcher entiende patrones tipo "/api/v1/usuarios/{id}" o
    // "/api/v1/roles/{idRol}/actividades" y los compara contra la URL real
    // de la petición, respetando variables de path. Es el mismo matcher
    // que usa Spring internamente para resolver @RequestMapping.
    private val pathMatcher = AntPathMatcher()

    fun hasPermission(

        rol: String,

        method: String,

        url: String

    ): Boolean {

        val permisos =
            permissionRepository
                .obtenerPermisosPorRol(rol)

        return permisos.any { permiso ->

            permiso.getMetodoHttp().equals(method, ignoreCase = true) &&
                pathMatcher.match(permiso.getUrl(), url)

        }

    }

}
