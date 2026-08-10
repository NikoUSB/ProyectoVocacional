package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "password_reset_token")
class PasswordResetTokenEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "id_usuario", nullable = false)
    var idUsuario: Long,

    @Column(name = "token", nullable = false, length = 100, unique = true)
    var token: String,

    @Column(name = "fecha_expiracion", nullable = false)
    var fechaExpiracion: LocalDateTime,

    @Column(name = "usado", nullable = false)
    var usado: Boolean = false,

    @Column(name = "fecha_creacion", nullable = false)
    var fechaCreacion: LocalDateTime

)
