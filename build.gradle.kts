import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.io.IOException

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
  version = "git rev-parse --short=8 HEAD".runCommand(workingDir = rootDir)

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

/**
 * cli 실행 결과를 반환한기 위한 함수
 */
fun String.runCommand(
  workingDir: File = File("."),
  timeoutAmount: Long = 60,
  timeoutUnit: TimeUnit = TimeUnit.SECONDS,
): String =
  ProcessBuilder(split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)".toRegex()))
    .directory(workingDir)
    .redirectOutput(ProcessBuilder.Redirect.PIPE)
    .redirectError(ProcessBuilder.Redirect.PIPE)
    .start()
    .apply { waitFor(timeoutAmount, timeoutUnit) }
    .run {
      val error = errorStream.bufferedReader().readText().trim()
      if (error.isNotEmpty()) {
        throw IOException(error)
      }
      inputStream.bufferedReader().readText().trim()
    }
