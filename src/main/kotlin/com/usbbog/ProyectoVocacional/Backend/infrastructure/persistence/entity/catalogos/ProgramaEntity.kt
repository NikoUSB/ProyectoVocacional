package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos

import jakarta.persistence.*

@Entity
@Table(
    name = "programa",
    schema = "catalogos"
)
class ProgramaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(
        name = "nombre",
        nullable = false,
        unique = true,
        length = 150
    )
    var nombrePrograma: String,

    @Column(name = "descripcion")
    var descripcion: String? = null,

    @Column(name = "url")
    var url: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_area",
        nullable = false
    )
    var area: AreaEntity,

    @Column(
    name = "activo",
    nullable = false
    )
    var activo: Boolean = true

)