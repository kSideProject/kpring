import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  id("org.springframework.boot") version "3.2.4"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.23"
  kotlin("plugin.spring") version "1.9.23"
  id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
  kotlin("kapt") version "1.9.23"
}

repositories {
  // Required to download KtLint
  mavenCentral()
}

allprojects {
  group = "com.sideproject"
  version = "0.0.1-SNAPSHOT"

  repositories {
    mavenCentral()
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs += "-Xjsr305=strict"
      jvmTarget = "21"
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}

// disable tasks bootJar
tasks.withType<BootJar> {
  enabled = false
}

subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

  repositories {
    // Required to download KtLint
    mavenCentral()
  }

  // Optionally configure plugin
  configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    debug.set(true)
  }
}
