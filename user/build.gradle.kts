plugins {
  id("org.springframework.boot") version "3.2.4"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.23"
  kotlin("plugin.spring") version "1.9.23"
  kotlin("plugin.jpa") version "1.9.23"

  // open api3
  id("com.epages.restdocs-api-spec") version "0.19.2"
  // jib
  id("com.google.cloud.tools.jib") version "3.4.0"
}

dependencies {
  implementation(project(":core"))
  // MySQL
  runtimeOnly("com.mysql:mysql-connector-j")
  // JPA
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  // WEB
  implementation("org.springframework.boot:spring-boot-starter-web")
  // JACKSON
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  // validation
  implementation("org.springframework.boot:spring-boot-starter-validation")
  // security
  implementation("org.springframework.boot:spring-boot-starter-security")

  // lombok
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  // TEST
  testImplementation(project(":test"))
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  // kotest
  testImplementation("io.kotest:kotest-assertions-core")
  testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
  // mockk
  testImplementation("io.mockk:mockk:1.13.10")
  testImplementation("com.ninja-squad:springmockk:4.0.2")
  testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

  // Spring rest docs
  implementation("org.springframework.restdocs:spring-restdocs-webtestclient")
  implementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

openapi3 {
  setServer("http://localhost/user")
  title = "User API"
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
    image = "localhost:5000/user-application"
    setAllowInsecureRegistries(true)
    tags = setOf("latest")
  }
  container {
    jvmFlags = listOf("-Xms512m", "-Xmx512m")
  }
}

tasks.getByName("jib").dependsOn("openapi3")
