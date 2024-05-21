package kpring.server

import kpring.core.global.config.WebMvcStaticResourceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(WebMvcStaticResourceConfig::class)
class ServerApplication

fun main(args: Array<String>) {
  runApplication<ServerApplication>(*args)
}
