package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "logs"
)
class LogsEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "id_usuario", nullable = false)
    var idUsuario: Long,

    @Column(name = "id_usuario_alterado")
    var idUsuarioAlterado: Long? = null,

    @Column(name = "id_actividad", nullable = false)
    var idActividad: Long,

    @Column(name = "descripcion")
    var descripcion: String? = null,

    @Column(name = "fecha", nullable = false)
    var fecha: LocalDateTime = LocalDateTime.now(),

    @Column(name = "estado", nullable = false)
    var estado: Boolean = true

)