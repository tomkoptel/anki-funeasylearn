plugins {
    kotlin("jvm") version "1.4.20"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("io.gitlab.arturbosch.detekt") version "1.10.0"
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
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    detekt {
        config = rootProject.files("$rootDir/detekt.yml")
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
}
