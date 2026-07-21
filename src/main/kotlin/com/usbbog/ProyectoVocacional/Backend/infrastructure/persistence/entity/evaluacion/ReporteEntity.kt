package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import jakarta.persistence.*

@Entity
@Table(
    name = "reporte",
    schema = "evaluacion"
)
class ReporteEntity(

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

    @Column(
        name = "ruta_archivo",
        nullable = false
    )
    var rutaArchivo: String
)