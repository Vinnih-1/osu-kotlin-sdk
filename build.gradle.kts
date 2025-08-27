@file:Suppress("PropertyName")

plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("com.vanniktech.maven.publish") version "0.34.0"
    id("signing")
    id("org.jetbrains.dokka") version "2.0.0"
    jacoco
}

group = "io.github.vinnih-1"
version = "0.3.1-alpha"

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(groupId = group.toString(), version = version.toString(), artifactId = "osukdk")

    pom {
        name.set("osu-kotlin-sdk")
        description.set("Kotlin wrapper for the osu! v2 API")
        url.set("https://github.com/Vinnih-1/osu-kotlin-sdk")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("vinnih-1")
                name.set("Vinícius Albert")
                email.set("vinifantal@hotmail.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/Vinnih-1/osu-kotlin-sdk.git")
            developerConnection.set("scm:git:ssh://github.com/Vinnih-1/osu-kotlin-sdk.git")
            url.set("https://github.com/Vinnih-1/osu-kotlin-sdk")
        }
    }
}

tasks.withType<PublishToMavenRepository>().configureEach {
    outputs.upToDateWhen { false }
}

tasks.withType<GenerateMavenPom>().configureEach {
    outputs.upToDateWhen { false }
}

repositories {
    mavenCentral()
}

val ktor_version: String by project
val json_version: String by project
val coroutine_test_version: String by project
val logback_version: String by project

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$json_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutine_test_version")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-auth:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

    runtimeOnly("ch.qos.logback:logback-classic:$logback_version")
}

tasks.test {
    useJUnitPlatform()
    exclude("**/ForumEndpointsTest.class")
    exclude("**/OAuthTokensEndpointTest.class")
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
    }
}

signing {
    val rawKey = project.findProperty("signing.key") as String? ?: System.getenv("SIGNING_KEY")
    val signingKey = rawKey?.replace("\\n", "\n")
    val signingPassword = project.findProperty("signing.password") as String? ?: System.getenv("SIGNING_PASSWORD")

    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}

kotlin {
    jvmToolchain(11)
}