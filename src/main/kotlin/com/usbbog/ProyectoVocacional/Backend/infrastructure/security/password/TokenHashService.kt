package com.usbbog.proyectovocacional.backend.infrastructure.security.password

import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class TokenHashService {

    fun hash(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
