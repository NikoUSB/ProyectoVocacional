package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "logs",
    schema = "seguridad"
)
class LogsEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_usuario_alterado",
        nullable = false
    )
    var usuario: UsuarioEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "id_actividad",
        nullable = false
    )
    var actividad: ActividadEntity,

    @Column(name = "descripcion")
    var descripcion: String? = null,

    @Column(
        name = "fecha",
        nullable = false
    )
    var fecha: LocalDateTime = LocalDateTime.now()
)