package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "prueba"
)
class PruebaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "id_usuario", nullable = false)
    var idUsuario: Long,

    @Column(name = "fecha", nullable = false)
    var fecha: LocalDateTime = LocalDateTime.now(),

    @Column(name = "tiempo_invertido")
    var tiempoInvertido: Int? = null,

    @Column(name = "version_prueba", length = 50)
    var versionPrueba: String? = null,

    @Column(name = "satisfaccion")
    var satisfaccion: Short? = null,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)