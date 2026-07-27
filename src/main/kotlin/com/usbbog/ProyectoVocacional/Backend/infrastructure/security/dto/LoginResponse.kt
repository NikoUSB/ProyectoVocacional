package com.usbbog.proyectovocacional.backend.infrastructure.security.dto

data class LoginResponse(

    val token:String,

    val type:String="Bearer",

    val expiresIn: Long,

    val username:String,

    val rol:String

)