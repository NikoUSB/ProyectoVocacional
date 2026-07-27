package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.evaluacion

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "afinidad_programa"
)
class AfinidadProgramaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "id_programa", nullable = false)
    var idPrograma: Long,

    @Column(name = "id_prueba", nullable = false)
    var idPrueba: Long,

    @Column(name = "valor_afinidad", nullable = false, precision = 5, scale = 2)
    var valorAfinidad: BigDecimal,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

)