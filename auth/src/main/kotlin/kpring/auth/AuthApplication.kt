package kpring.auth

import kpring.core.global.config.WebFluxStaticResourceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(WebFluxStaticResourceConfig::class)
class AuthApplication

fun main(args: Array<String>) {
  runApplication<AuthApplication>(*args)
}
