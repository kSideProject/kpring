plugins {
  id("org.springframework.boot") version "3.2.4"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.23"
  kotlin("plugin.spring") version "1.9.23"

  // asciidoctor plugin
  id("org.asciidoctor.jvm.convert") version "3.3.2"
  // open api3
  id("com.epages.restdocs-api-spec") version "0.19.2"
  // jib
  id("com.google.cloud.tools.jib") version "3.4.0"
}

dependencies {
  // core module
  implementation(project(":core"))

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  // validation
  implementation("org.springframework.boot:spring-boot-starter-validation")

  // webflux
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  // jwt
  implementation("io.jsonwebtoken:jjwt-api:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

  // reactive redis
  implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

  // test module
  testImplementation(project(":test"))

  // dev tool
  developmentOnly("org.springframework.boot:spring-boot-devtools")

  // kotest
  testImplementation("io.kotest:kotest-assertions-core")
  testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
  // mockk
  testImplementation("io.mockk:mockk:1.13.10")
  testImplementation("com.ninja-squad:springmockk:4.0.2")
  // Spring rest docs
  testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
  testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
  testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
  // test container
  testImplementation("org.springframework.boot:spring-boot-testcontainers")

  // default test
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
}

extra["snippetsDir"] = file("build/generated-snippets")

tasks.test {
  outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
  inputs.dir(project.extra["snippetsDir"]!!)
  dependsOn(tasks.test)
}

openapi3 {
  setServer("http://localhost/auth")
  title = "Auth API"
  description = "API document"
  version = "0.1.0"
  format = "yaml"
  outputDirectory = "src/main/resources/static"
}

jib {
  from {
    image = "eclipse-temurin:21-jre"
  }
  to {
    image = "youdong98/kpring-auth-application"
    setAllowInsecureRegistries(true)
    tags = setOf("latest")
  }
  container {
    jvmFlags = listOf("-Xms512m", "-Xmx512m")
  }
}

tasks.getByName("jib").dependsOn("openapi3")
