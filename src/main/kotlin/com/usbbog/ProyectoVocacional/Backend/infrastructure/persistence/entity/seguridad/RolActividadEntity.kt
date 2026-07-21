package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*

@Entity
@Table(
    name = "rol_actividad",
    schema = "seguridad",
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["id_rol", "id_actividad"]
        )
    ]
)
class RolActividadEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_rol",
        nullable = false
    )
    var rol: RolEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_actividad",
        nullable = false
    )
    var actividad: ActividadEntity,

    @Column(
        name = "estado",
        nullable = false
    )
    var activo: Boolean = true
)