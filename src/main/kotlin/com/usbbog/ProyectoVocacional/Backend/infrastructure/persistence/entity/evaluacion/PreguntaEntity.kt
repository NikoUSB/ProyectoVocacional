package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import jakarta.persistence.*

@Entity
@Table(
    name = "pregunta"
)
class PreguntaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "id_programa", nullable = false)
    var idPrograma: Long,

    @Column(name = "enunciado", nullable = false)
    var enunciado: String,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)