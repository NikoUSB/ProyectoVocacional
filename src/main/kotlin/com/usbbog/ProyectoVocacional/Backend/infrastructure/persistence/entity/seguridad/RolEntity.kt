package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*

@Entity
@Table(
    name = "rol"
)
class RolEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "nombre_rol", nullable = false, length = 100, unique = true)
    var nombreRol: String,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)