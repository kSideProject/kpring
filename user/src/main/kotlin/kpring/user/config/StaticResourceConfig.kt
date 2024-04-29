package kpring.user.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Profile("local")
@Configuration
class StaticResourceConfig : WebMvcConfigurer{

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/static/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "FETCH")
    }
}