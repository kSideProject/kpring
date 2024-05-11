plugins {
  id("org.springframework.boot") version "3.2.4"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.23"
  kotlin("plugin.spring") version "1.9.23"
}

group = "kpring"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
  /** Core Module */
  implementation(project(":core"))

  /** Spring Boot */
  implementation("org.springframework.boot:spring-boot-starter") // Default
  implementation("org.springframework.boot:spring-boot-starter-web") // Web
  implementation("org.springframework.boot:spring-boot-starter-websocket") // Boot Websocket
  implementation("org.springframework.restdocs:spring-restdocs-webtestclient") // Spring rest docs
  implementation("org.springframework.restdocs:spring-restdocs-asciidoctor")

  /** Lombok */
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  /** Kotlin */
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  /** jpa */
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  runtimeOnly("com.mysql:mysql-connector-j")

  /** test */
  testImplementation(project(":test"))
  testImplementation("org.springframework.boot:spring-boot-starter-test") // Test
  testImplementation("io.projectreactor:reactor-test")
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
}
