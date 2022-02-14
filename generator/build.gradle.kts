plugins {
    kotlin("jvm")
    jacoco
    id("com.github.gmazzo.buildconfig")
}

buildConfig {
    val shutterStockConsumerKey: String? by project
    val shutterStockConsumerSecret: String? by project
    buildConfigField("String", "SHUTTER_STOCK_CONSUMER_KEY", "\"${shutterStockConsumerKey}\"")
    buildConfigField("String", "SHUTTER_STOCK_CONSUMER_SECRET", "\"${shutterStockConsumerSecret}\"")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":words"))
    implementation(project(":phrases"))
    implementation(project(":fel-words"))

    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.google.code.gson:gson:2.9.0")

    implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0")

    testImplementation("org.amshove.kluent:kluent:1.68")
    testImplementation("org.mockito:mockito-inline:4.3.1")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("com.airbnb.okreplay:okreplay:1.6.0")
    testImplementation("com.airbnb.okreplay:junit:1.6.0")
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)

    violationRules {
        rule {
            limit {
                minimum = "0".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}
