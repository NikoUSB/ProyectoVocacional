package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.ProgramaEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "usuario",
    schema = "seguridad"
)
class UsuarioEntity(

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
    @JoinColumn(name = "id_programa")
    var programa: ProgramaEntity? = null,

    @Column(
        name = "nombre",
        nullable = false,
        length = 100
    )
    var nombre: String,

    @Column(
        name = "apellidos",
        nullable = false,
        length = 100
    )
    var apellidos: String,

    @Column(
        name = "documento",
        nullable = false,
        unique = true,
        length = 30
    )
    var documento: String,

    @Column(
        name = "correo",
        nullable = false,
        unique = true,
        length = 150
    )
    var correo: String,

    @Column(name = "edad")
    var edad: Int? = null,

    @Column(name = "telefono")
    var telefono: String? = null,

    @Column(name = "genero")
    var genero: String? = null,

    @Column(name = "genero_otro")
    var generoOtro: String? = null,

    @Column(name = "departamento")
    var departamento: String? = null,

    @Column(name = "ciudad")
    var ciudad: String? = null,

    @Column(
        name = "contrasena_hash",
        nullable = false
    )
    var contrasenaHash: String,

    @Column(name = "tipo_usuario")
    var tipoUsuario: String? = null,

    @Column(
        name = "fecha_creacion",
        nullable = false
    )
    var fechaCreacion: LocalDateTime = LocalDateTime.now(),

    @Column(name = "semestre")
    var semestre: Int? = null
)