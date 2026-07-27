package com.usbbog.proyectovocacional.backend.infrastructure.security.jwt

import com.usbbog.proyectovocacional.backend.infrastructure.security.userdetails.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(

    private val jwtService: JwtService,

    private val userDetailsService: CustomUserDetailsService

) : OncePerRequestFilter() {


    override fun doFilterInternal(

        request: HttpServletRequest,

        response: HttpServletResponse,

        filterChain: FilterChain

    ) {

        println("JWT FILTER EJECUTADO")
        println("AUTH HEADER: ${request.getHeader("Authorization")}")


        val authHeader = request.getHeader("Authorization")


        if (
            authHeader == null ||
            !authHeader.startsWith("Bearer ")
        ) {

            filterChain.doFilter(
                request,
                response
            )

            return
        }


        try {

            val token = authHeader.substring(7)


            if (jwtService.isTokenValid(token)) {

                println("TOKEN VALIDO")


                val username =
                    jwtService.extractUsername(token)


                println("USUARIO JWT: $username")


                if (
                    SecurityContextHolder
                        .getContext()
                        .authentication == null
                ) {


                    val userDetails =
                        userDetailsService
                            .loadUserByUsername(username)

                    println("USER DETAILS: $userDetails")
                    println("AUTHORITIES: ${userDetails.authorities}")


                    val authentication =
                        UsernamePasswordAuthenticationToken(

                            userDetails,

                            null,

                            userDetails.authorities

                        )


                    authentication.details =
                        WebAuthenticationDetailsSource()
                            .buildDetails(request)


                    SecurityContextHolder
                        .getContext()
                        .authentication = authentication


                    println(
                        "AUTH CREADA: ${SecurityContextHolder.getContext().authentication}"
                    )

                    println(
                        "SECURITY CONTEXT: ${
                            SecurityContextHolder
                                .getContext()
                                .authentication
                        }"
                    )

                }

            }


        } catch (e: Exception) {

            println("ERROR JWT: ${e.message}")

            SecurityContextHolder
                .clearContext()

        }


        filterChain.doFilter(
            request,
            response
        )

    }

}