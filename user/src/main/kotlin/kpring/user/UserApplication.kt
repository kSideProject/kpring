package kpring.user

import kpring.core.global.config.WebMvcStaticResourceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@Import(WebMvcStaticResourceConfig::class)
class UserApplication

fun main(args: Array<String>) {
  runApplication<UserApplication>(*args)
}
