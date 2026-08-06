package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos

import jakarta.persistence.*

@Entity
@Table(
    name = "programa"
)
class ProgramaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "nombre", nullable = false, unique = true, length = 150)
    var nombrePrograma: String,

    @Column(name = "descripcion")
    var descripcion: String? = null,

    @Column(name = "url")
    var url: String? = null,

    @Column(name="id_area", nullable = false)
    var idArea:Long,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true,

    @Column("path_logo")
    var pathLogo: String? = null

)