package com.usbbog.proyectovocacional.backend.infrastructure.persistence.entity.catalogos.lugares

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(
    name = "municipios"
)
class MunicipioEntity (

    @Id
    @Column(name = "id_municipio")
    var idMunicipio: String,

    @Column(name = "nombre")
    var nombreMunicipio: String,

    @Column(name = "id_departamento")
    var idDepartamento: String

)