package com.usbbog.proyectovocacional.backend.infrastructure.security.permissions

import com.usbbog.proyectovocacional.backend.infrastructure.security.userdetails.CustomUserDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class PermissionFilter(

    private val permissionService: PermissionService

) : OncePerRequestFilter() {

    override fun shouldNotFilter(

        request: HttpServletRequest

    ): Boolean {

        val url = request.requestURI

        return (

            // El navegador/cliente hace preflight sin JWT, nunca debe evaluarse.
            HttpMethod.OPTIONS.matches(request.method) ||

                url == "/" ||

                url.startsWith("/swagger") ||

                url.startsWith("/swagger-ui") ||

                url.startsWith("/v3/api-docs") ||

                url.startsWith("/api/v1/auth") ||

                url.startsWith("/api/v1/departamentos") ||

                url.startsWith("/api/v1/catalogos")

            )

    }

    override fun doFilterInternal(

        request: HttpServletRequest,

        response: HttpServletResponse,

        filterChain: FilterChain

    ) {

        val authentication =
            SecurityContextHolder
                .getContext()
                .authentication

        // Si el JwtFilter no logró autenticar, dejamos que la cadena de
        // Spring Security responda 401 mediante el authenticationEntryPoint.
        if (

            authentication == null ||

            !authentication.isAuthenticated

        ) {

            filterChain.doFilter(
                request,
                response
            )

            return

        }

        val principal =
            authentication.principal

        if (

            principal !is CustomUserDetails

        ) {

            filterChain.doFilter(
                request,
                response
            )

            return

        }

        val rol =
            principal.getRol()

        val permitido =
            permissionService
                .hasPermission(

                    rol,

                    request.method,

                    request.requestURI

                )

        if (!permitido) {

            response.sendError(

                HttpServletResponse.SC_FORBIDDEN,

                "No posee permisos para acceder al recurso."

            )

            return

        }

        filterChain.doFilter(

            request,
            response

        )

    }

}
