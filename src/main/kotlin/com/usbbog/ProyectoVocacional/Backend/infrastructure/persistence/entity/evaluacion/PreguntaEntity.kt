package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.ProgramaEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "pregunta",
    schema = "evaluacion"
)
class PreguntaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_programa",
        nullable = false
    )
    var programa: ProgramaEntity,

    @Column(
        name = "enunciado",
        nullable = false
    )
    var enunciado: String,

    @Column(
        name = "activa",
        nullable = false
    )
    var activa: Boolean = true
)