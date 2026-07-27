package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos

import jakarta.persistence.*

@Entity
@Table(
    name = "area",
)
class AreaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "nombre", nullable = false, length = 150, unique = true)
    var nombreArea: String,

    @Column(name = "descripcion_area")
    var descripcionArea: String,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)