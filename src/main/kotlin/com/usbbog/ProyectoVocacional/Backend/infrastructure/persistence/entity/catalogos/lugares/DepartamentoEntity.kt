package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.lugares

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(
    name = "departamentos"
)
class DepartamentoEntity (
    @Id
    @Column(name = "id_departamento")
    var idDepartamento: String,

    @Column(name = "nombre")
    var nombreDepartamento: String

)