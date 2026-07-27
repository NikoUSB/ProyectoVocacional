package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import jakarta.persistence.*

@Entity
@Table(
    name = "reporte"
)
class ReporteEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "id_prueba", nullable = false)
    var idPrueba: Long,

    @Column(name = "ruta_archivo", nullable = false)
    var rutaArchivo: String,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)