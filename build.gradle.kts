import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.20"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("io.gitlab.arturbosch.detekt") version "1.10.0"
    id("com.github.gmazzo.buildconfig") version "2.0.2"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.4.3")
    }
}

allprojects {
    group = "com.olderwold.anki.funeasylearn"
    version = "1.0-SNAPSHOT"

    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        debug.set(false)
        version.set("0.40.0")
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(true)
        enableExperimentalRules.set(true)
        filter {
            exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
            include("**/kotlin/**")
        }
    }

    detekt {
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }

    val ktlint = tasks.withType<org.jlleitschuh.gradle.ktlint.KtlintCheckTask>()
    val detekt = tasks.withType<io.gitlab.arturbosch.detekt.Detekt>()
    tasks.matching { it.name.contains("check") }
        .configureEach {
            this.dependsOn(ktlint)
            this.dependsOn(detekt)
        }

    detekt.configureEach {
        exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
