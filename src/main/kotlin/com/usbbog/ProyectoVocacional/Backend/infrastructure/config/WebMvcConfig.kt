package com.usbbog.proyectovocacional.backend.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

@Configuration
class WebMvcConfig(
    private val appProperties: AppProperties
) : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val uploadPath = Paths.get(appProperties.uploadDir).toAbsolutePath().toUri().toString()
        registry.addResourceHandler("/uploads/pacho/**")
            .addResourceLocations(uploadPath)
    }
}
