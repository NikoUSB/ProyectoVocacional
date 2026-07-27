package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.seguridad

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "usuario"
)
class UsuarioEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name="id_rol", nullable = false)
    var idRol:Long,

    @Column(name = "nombre_usuario", nullable = false, length = 50, unique = true)
    var nombreUsuario: String? = null,

    @Column(name = "contrasena_hash", nullable = false)
    var contrasenaHash: String,

    @Column(name = "documento", nullable = false, length = 30, unique = true)
    var documento: String,

    @Column(name = "nombre", nullable = false, length = 100)
    var nombre: String,

    @Column(name = "apellidos", nullable = false, length = 100)
    var apellidos: String,

    @Column(name = "correo", nullable = false, length = 150, unique = true)
    var correo: String,

    @Column(name = "telefono", length = 30)
    var telefono: String? = null,

    @Column(name = "fecha_nacimiento")
    var fechaNacimiento: LocalDate? = null,

    @Column(name = "genero", length = 30)
    var genero: String? = null,

    @Column(name = "genero_otro", length = 100)
    var generoOtro: String? = null,

    @Column(name = "departamento", length = 100)
    var departamento: String? = null,

    @Column(name = "ciudad", length = 100)
    var ciudad: String? = null,

    @Column(name="id_programa")
    var idPrograma:Long?,

    @Column(name = "semestre")
    var semestre: Int? = null,

    @Column(name = "fecha_creacion", nullable = false)
    var fechaCreacion: LocalDateTime? = null,

    @Column(name = "estado", nullable = false)
    var activo: Boolean = true

//    @Column(name = "fecha_actualizacion")
//    var fechaActualizacion: LocalDateTime? = null

)