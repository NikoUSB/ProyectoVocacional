package com.usbbog.proyectovocacional.backend.infrastructure.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(

    private val properties: JwtProperties

) {


    private fun getSignInKey(): SecretKey {

        return Keys.hmacShaKeyFor(

            properties.secret
                .toByteArray(
                    StandardCharsets.UTF_8
                )

        )

    }

    fun generateToken(username: String, rol: String): String {
        val now = Date()
        val expirationDate = Date(
            now.time +
                    properties.expiration
        )
        return Jwts.builder()
            .subject(username)
            .claim(
                "rol",
                rol
            )
            .issuedAt(now)
            .expiration(expirationDate)
            .signWith(
                getSignInKey(),
                Jwts.SIG.HS256
            )
            .compact()


    }


    fun extractUsername(

        token: String

    ): String {

        return extractAllClaims(

            token

        ).subject

    }


    fun extractRol(

        token: String

    ): String {

        return extractAllClaims(
            token
        )["rol"] as String

    }


    fun isTokenValid(token:String):Boolean {

        return try {

            !isTokenExpired(token)

        } catch (e: Exception) {

            println("ERROR JWT: ${e.message}")
            false

        }
    }


    private fun isTokenExpired(

        token: String

    ): Boolean {

        return extractAllClaims(
            token
        ).expiration.before(

            Date()

        )

    }


    private fun extractAllClaims(

        token: String

    ): Claims {

        return Jwts.parser()

            .verifyWith(
                getSignInKey()
            )

            .build()

            .parseSignedClaims(
                token
            )

            .payload

    }

}