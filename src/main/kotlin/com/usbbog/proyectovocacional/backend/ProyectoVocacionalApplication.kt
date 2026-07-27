package com.usbbog.proyectovocacional.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ProyectoVocacionalApplication

fun main(args: Array<String>) {
    runApplication<ProyectoVocacionalApplication>(*args)
}