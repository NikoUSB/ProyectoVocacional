package com.usbbog.proyectovocacional.backend.application.dto.response.usuario

import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Municipio

data class UsuarioPerfilResponse(

    val nombre: String,
    val apellidos: String,
    val telefono: String?,
    val genero: String?,
    val generoOtro: String?,
    val departamento: String?,
    val municipio: String?,
    val programa: String?,
    val semestre: Int?

)