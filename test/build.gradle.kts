plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // kotest
    implementation("io.kotest:kotest-assertions-core")
    implementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
    // mockk
    implementation("io.mockk:mockk:1.13.10")
    implementation("com.ninja-squad:springmockk:4.0.2")
    // Spring rest docs
    implementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    implementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    implementation("org.springframework.restdocs:spring-restdocs-asciidoctor")

    implementation("org.springframework.boot:spring-boot-starter-test")
}
