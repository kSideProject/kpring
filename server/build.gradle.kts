import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.2.4"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.23"
  kotlin("plugin.spring") version "1.9.23"
}

group = "kpring"
version = "0.0.1-SNAPSHOT"

java {
//    sourceCompatibility = JavaVersion.VERSION_17
  sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
  mavenCentral()
}

dependencies {
  /** Core Module */
  implementation(project(":core"))

  /** Spring Boot */
  implementation("org.springframework.boot:spring-boot-starter") // Default
  implementation("org.springframework.boot:spring-boot-starter-web") // Web
  implementation("org.springframework.boot:spring-boot-starter-websocket") // Boot Websocket
  testImplementation("org.springframework.boot:spring-boot-starter-test") // Test
  testImplementation("io.projectreactor:reactor-test")
  implementation("org.springframework.restdocs:spring-restdocs-webtestclient") // Spring rest docs
  implementation("org.springframework.restdocs:spring-restdocs-asciidoctor")

  /** Lombok */
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  /** Kotlin */
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  testImplementation("io.kotest:kotest-assertions-core") // Kotest
  testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
//        jvmTarget = "17"
    jvmTarget = "21"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
