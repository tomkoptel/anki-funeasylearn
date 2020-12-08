plugins {
    kotlin("jvm")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("WordsDb") {
        packageName = "com.olderwold.anki.funeasylearn.words"
        sourceFolders = listOf("sqldelight")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.squareup.sqldelight:sqlite-driver:1.4.3")
}
