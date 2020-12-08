rootProject.name = "anki-funeasylearn"
include("generator")
includeDb("fel-words")
includeDb("phrases")
includeDb("words")

fun includeDb(name: String) {
    include(":$name")
    project(":$name").projectDir = file("db/$name")
}
