package com.usbbog.proyectovocacional.backend.infrastructure.security.config

import com.usbbog.proyectovocacional.backend.infrastructure.security.jwt.JwtFilter
import com.usbbog.proyectovocacional.backend.infrastructure.security.permissions.PermissionFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(

    private val jwtFilter: JwtFilter,
    private val permissionFilter: PermissionFilter,
    private val corsConfigurationSource: CorsConfigurationSource

) {


    @Bean
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {


        return http

            .csrf {
                it.disable()
            }

            .cors {
                it.configurationSource(corsConfigurationSource)
            }

            .authorizeHttpRequests {

                it

                    .requestMatchers(

                        "/",
                        "/swagger",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/api/v1/auth/**",
                        "/api/v1/departamentos/**",
                        "/api/v1/catalogos/**"

                    )
                    .permitAll()


                    .anyRequest()
                    .authenticated()

            }

            .exceptionHandling {

                it.authenticationEntryPoint { request, response, authException ->

                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "No autenticado"
                    )

                }

                it.accessDeniedHandler { request, response, accessDeniedException ->

                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Sin permisos"
                    )

                }

            }


            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

            .addFilterAfter(

                permissionFilter,

                JwtFilter::class.java

            )

            .build()

    }

}