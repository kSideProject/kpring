import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  id("org.springframework.boot") version "3.2.4"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.23"
  kotlin("plugin.spring") version "1.9.23"
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // data class serialize
  // validation
  implementation("org.springframework.boot:spring-boot-starter-validation")
  // tomcat
  implementation("org.springframework.boot:spring-boot-starter-web")
  // netty
  compileOnly("org.springframework.boot:spring-boot-starter-webflux")

  testImplementation(project(":test"))
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
  withType<BootJar> {
    enabled = false
  }
}
