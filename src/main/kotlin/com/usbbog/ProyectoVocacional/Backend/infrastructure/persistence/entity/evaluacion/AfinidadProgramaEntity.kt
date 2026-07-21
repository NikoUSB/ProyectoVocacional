package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.ProgramaEntity
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "afinidad_programa",
    schema = "evaluacion"
)
class AfinidadProgramaEntity(

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_prueba",
        nullable = false
    )
    var prueba: PruebaEntity,

    @Column(
        name = "valor_afinidad",
        precision = 5,
        scale = 2,
        nullable = false
    )
    var valorAfinidad: BigDecimal
)