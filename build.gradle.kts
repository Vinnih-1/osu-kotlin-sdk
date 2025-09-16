@file:Suppress("PropertyName")

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
    alias(libs.plugins.dokka)
    alias(libs.plugins.jacoco)
}

group = "io.github.vinnih-1"
version = "1.1.0-beta"

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

dependencies {
    implementation(kotlin("reflect"))
    implementation(libs.kotlinx.json)
    implementation(libs.kotlinx.coroutines.test)

    implementation(libs.client.core)
    implementation(libs.client.cio)
    implementation(libs.client.auth)
    implementation(libs.client.logging)
    implementation(libs.client.content.negotiation)

    runtimeOnly(libs.logback.classic)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
    exclude("**/ForumEndpointsTest.class")
    exclude("**/OAuthTokensEndpointTest.class")
    exclude("**/ChatEndpointsTest.class")
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
    }
}

signing {
    val secretKeyFile = File(System.getenv("SECRET_KEY_FILE"))
    val signingPassword = project.findProperty("signing.password") as String? ?: System.getenv("SIGNING_PASSWORD")

    if (secretKeyFile.exists() && signingPassword != null) {
        useGpgCmd()
        useInMemoryPgpKeys(secretKeyFile.readText(), signingPassword)
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}