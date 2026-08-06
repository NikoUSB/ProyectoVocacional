package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Municipio
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.LugarRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.catalogo.lugares.DepartamentoMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class LugarService (
    private val lugarRepository: LugarRepository
){

    fun obtenerDepartamentos () : List<Departamento> {
        val departamentos = lugarRepository.obtenerDepartamentos()

        if (departamentos.isEmpty())
        {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se enccuentraron Departamentos."
            )
        }

        return departamentos
    }

    fun obtenerMunicipioPorDepartamento(id: String): List<Municipio> {

        val departamento = lugarRepository.obtenerDepartamentoPorId(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Departamento con id $id no encontrado."
            )

        val municipios = lugarRepository.obtenerMunicipiosPorDepartamento(id)

        if (municipios.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontraron municipios para el departamento ${departamento.nombreDepartamento}."
            )
        }

        return municipios
    }

}