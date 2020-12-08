plugins {
    kotlin("jvm")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("PhrasesDb") {
        packageName = "com.olderwold.anki.funeasylearn.phrases"
        sourceFolders = listOf("sqldelight")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.squareup.sqldelight:sqlite-driver:1.4.3")
}
