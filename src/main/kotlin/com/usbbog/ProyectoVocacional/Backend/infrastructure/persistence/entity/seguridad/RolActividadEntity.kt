package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*

@Entity
@Table(
    name = "rol_actividad"
)
class RolActividadEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "id_rol", nullable = false)
    var idRol: Long,

    @Column(name = "id_actividad", nullable = false)
    var idActividad: Long,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)