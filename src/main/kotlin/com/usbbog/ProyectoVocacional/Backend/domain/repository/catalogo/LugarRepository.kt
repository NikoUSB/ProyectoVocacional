package com.usbbog.proyectovocacional.backend.domain.repository.catalogo

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Municipio

interface LugarRepository {

    fun obtenerDepartamentos(): List<Departamento>

    fun obtenerDepartamentoPorId(idDepartamento: String): Departamento?

    fun obtenerMunicipiosPorDepartamento(idDepartamento: String): List<Municipio>
}