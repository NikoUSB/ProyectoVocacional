package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad.UsuarioEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "prueba",
    schema = "evaluacion"
)
class PruebaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_usuario",
        nullable = false
    )
    var usuario: UsuarioEntity,

    @Column(
        name = "fecha",
        nullable = false
    )
    var fecha: LocalDateTime = LocalDateTime.now(),

    @Column(name = "tiempo_invertido")
    var tiempoInvertido: Int? = null,

    @Column(name = "version_prueba")
    var versionPrueba: String? = null,

    @Column(name = "satisfaccion")
    var satisfaccion: Short? = null
)