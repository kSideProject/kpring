plugins {
  id("org.springframework.boot") version "3.2.4"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.23"
  kotlin("plugin.spring") version "1.9.23"
  kotlin("kapt") version "1.9.23"
  // open api3
  id("com.epages.restdocs-api-spec") version "0.19.2"
  // jib
  id("com.google.cloud.tools.jib") version "3.4.0"
}

val queryDslVersion = "5.1.0"

java {
  sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
  /** dev tool */
  developmentOnly("org.springframework.boot:spring-boot-devtools")

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

  /** spring data mongo & querydsl */
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
  implementation("com.querydsl:querydsl-mongodb:$queryDslVersion") {
    exclude("org.mongodb", "mongo-java-driver")
  }
  implementation("com.querydsl:querydsl-jpa:$queryDslVersion")
  kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

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

kapt {
  annotationProcessor("org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor")
}

openapi3 {
  setServer("http://localhost/server")
  title = "Server API"
  description = "API document"
  version = "0.1.0"
  format = "yaml"
  outputDirectory = "src/main/resources/static"
}

jib {
  from {
    image = "eclipse-temurin:21-jre"
    platforms {
      platform {
        architecture = "arm64"
        os = "linux"
      }
    }
  }
  to {
    image = "youdong98/kpring-server-application"
    setAllowInsecureRegistries(true)
    tags = setOf("latest")
  }
  container {
    jvmFlags = listOf("-Xms512m", "-Xmx512m")
  }
}

tasks.getByName("jib").dependsOn("openapi3")
