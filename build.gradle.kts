plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.keola"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.mapstruct:mapstruct:1.5.2.Final")  // Dependencia principal de MapStruct
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.2.Final")  // Procesador de anotaciones de MapStruct
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.10")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.0.10")
    runtimeOnly("org.postgresql:r2dbc-postgresql:1.0.2.RELEASE")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-api:2.8.3")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test:3.5.9")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
