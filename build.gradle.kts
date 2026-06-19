plugins {
    kotlin("jvm") version "2.3.10"
    id("java")
    scala
    application
}

group = "kr1v.index"
version = "0.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    implementation("org.scala-lang:scala3-library_3:3.3.5")
    implementation("com.lihaoyi:scalatags_3:0.13.1")
}

kotlin {
    jvmToolchain(25)
}

application {
    mainClass.set("kr1v.index.Main")
}

tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-release", "21")
}
