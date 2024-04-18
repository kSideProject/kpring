plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // JPA
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    // WEB
    implementation ("org.springframework.boot:spring-boot-starter-web")
    // JACKSON
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin")
    // H2
    runtimeOnly ("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
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
    implementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    implementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    implementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
}
