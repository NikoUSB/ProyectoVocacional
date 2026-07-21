package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import jakarta.persistence.*

@Entity
@Table(
    name = "respuesta",
    schema = "evaluacion"
)
class RespuestaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_prueba",
        nullable = false
    )
    var prueba: PruebaEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_pregunta",
        nullable = false
    )
    var pregunta: PreguntaEntity,

    @Column(
        name = "valor",
        nullable = false
    )
    var valor: Int
)