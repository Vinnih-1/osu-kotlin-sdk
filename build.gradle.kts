@file:Suppress("PropertyName")

plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"

    jacoco
}

group = "me.zumy.osukdk"
version = "0.1.0-ALPHA"

repositories {
    mavenCentral()
}

val ktor_version: String by project
val json_version: String by project
val coroutine_test_version: String by project

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$json_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutine_test_version")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
    }
}

kotlin {
    jvmToolchain(8)
}