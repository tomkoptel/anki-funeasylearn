plugins {
    kotlin("jvm")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("FelWordsDB") {
        packageName = "com.olderwold.anki.funeasylearn.fel"
        sourceFolders = listOf("sqldelight")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.squareup.sqldelight:sqlite-driver:1.4.3")
}
