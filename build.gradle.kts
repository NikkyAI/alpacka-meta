import com.squareup.sqldelight.gradle.SqlDelightDatabase
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

}

plugins {
    kotlin("jvm") version Kotlin.version
    id("kotlinx-serialization") version Kotlin.version
    id("com.github.johnrengelman.shadow") version "4.0.0"
    id("com.squareup.sqldelight") version SQDelight.version
    application
}

group = "moe.nikky"
version = "0.1-SNAPSHOT"

application {
//    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
}

sqldelight {
    methodMissing("CurseDatabase",
        arrayOf(
            closureOf<SqlDelightDatabase> {
//                className = "CurseDatabase"
                packageName = "moe.nikky.curseproxy.data"
                sourceFolders = listOf("sqldelight")
                schemaOutputDirectory = file("src/main/sqldelight/migrations")
            }
        )
    )
}


//war {
//    webAppDirName = "webapp"
//}

//tomcat {
//    contextPath = "/"
//    httpProtocol = "org.apache.coyote.http11.Http11Nio2Protocol"
//    ajpProtocol = "org.apache.coyote.ajp.AjpNio2Protocol"
//}

repositories {
    maven(url = "http://dl.bintray.com/kotlin/ktor")
    maven(url = "https://dl.bintray.com/kotlin/kotlinx")
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/pgutkowski/Maven")
    maven(url = "https://dl.bintray.com/kotlin/squash")
//    maven(url = "https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib-jdk8", Kotlin.version))
    compile(kotlin("reflect", Kotlin.version))
//    compile(kotlin("runtime", Kotlin.version))

    compile(ktor())
    compile(ktor("jackson"))
    compile(ktor("features"))
    compile(ktor("locations"))
    compile(ktor("html-builder"))
    compile(ktor("server-core"))
    compile(ktor("server-netty"))
    compile(ktor("server-servlet"))

//    tomcat("org.apache.tomcat.embed:tomcat-embed-core:${Tomcat.version}",
//            "org.apache.tomcat.embed:tomcat-embed-jasper:${Tomcat.version}")

    // Logging
    api(group = "ch.qos.logback", name = "logback-classic", version = Logback.version)

    // Networking
    api(group = "com.github.kittinunf.fuel", name = "fuel", version = Fuel.version)
    api(group = "com.github.kittinunf.fuel", name = "fuel-coroutines", version = Fuel.version)
    api(group = "com.github.kittinunf.fuel", name = "fuel-kotlinx-serialization", version = Fuel.version)

    // GraphQL
    api(group = "com.apurebase", name = "kgraphql", version = KGraphQL.version)

    // Dependency Injection
    api(group = "org.koin", name = "koin-ktor", version = Koin.version)

    // Database
    api(group = "com.squareup.sqldelight", name = "sqlite-driver", version = SQDelight.version)

    api(group = "org.jetbrains.squash", name = "squash-h2", version = Squash.version)

    // JSON
    api(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-runtime", version = Serialization.version)

    api(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.9.5")
    api(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.9.5")

    // Testing
    testCompile(group = "junit", name = "junit", version = "4.12")
    testCompile(ktor("server-test-host"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        apiVersion = "1.3"
        languageVersion = "1.3"
        jvmTarget = "1.8"
    }
}

kotlin {
//    experimental.coroutines = Coroutines.ENABLE
}

tasks.withType<Wrapper> {
    gradleVersion = Gradle.version
    distributionType = Gradle.distributionType // Wrapper.DistributionType.ALL
}