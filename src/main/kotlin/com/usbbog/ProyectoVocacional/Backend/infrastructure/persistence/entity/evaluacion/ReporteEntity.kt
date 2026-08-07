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

    @Column(name = "id_area_predominante")
    var idArea: Long,

    @Column(name = "id_programa_1")
    var idPrograma1: Long,

    @Column(name = "id_programa_2")
    var idPrograma2: Long,

    @Column(name = "id_programa_3")
    var idPrograma3: Long,


    @Column(name = "`nombre_archivo`", nullable = false)
    var nombreArchivo: String,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)