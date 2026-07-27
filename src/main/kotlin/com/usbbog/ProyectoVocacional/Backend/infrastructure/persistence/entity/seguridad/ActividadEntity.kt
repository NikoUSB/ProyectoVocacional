package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*

@Entity
@Table(
    name = "actividad"
)
class ActividadEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "nombre_act", nullable = false, unique = true, length = 150)
    var nombreActividad: String,

    @Column(name = "url")
    var url: String? = null,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)