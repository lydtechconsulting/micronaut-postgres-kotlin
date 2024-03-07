plugins {
    id("io.micronaut.application") version "4.3.2"
    id("io.micronaut.aot") version "4.3.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

version = "1.0.0"
group = "demo"

repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.serde:micronaut-serde-processor")

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.data:micronaut-data-jpa:4.6.2")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa:4.6.2")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut:micronaut-management")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.3")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.postgresql:postgresql")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")

    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.yaml:snakeyaml")

    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testImplementation(kotlin("test"))
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("dev.lydtech:component-test-framework:2.11.0")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.4.0")
    testImplementation("com.h2database:h2")
}

application {
    mainClass.set("demo.DemoApplication")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "demo.DemoApplication"
    }
}

kotlin {
    jvmToolchain(21)
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    include("**/*Test.*")
}

tasks.register<Test>("componentTest") {
    useJUnitPlatform()
    include("**/*CT.*")

    testLogging {
        events("passed", "skipped", "failed")
    }

    systemProperties(System.getProperties().toMap() as Map<String,Object>)
    environment("TESTCONTAINERS_REUSE_ENABLE", System.getProperty("containers.stayup"))
}
