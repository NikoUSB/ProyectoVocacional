package com.usbbog.proyectovocacional.backend.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "app"
)
data class AppProperties(

    val frontendUrl: String,

    val resetPasswordPath: String,

    val uploadDir: String = "./uploads/pacho"

)
