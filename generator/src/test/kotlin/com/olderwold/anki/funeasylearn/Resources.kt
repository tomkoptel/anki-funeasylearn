package com.olderwold.anki.funeasylearn

import java.io.File

fun Any.wordsDB(language: Language): File = resource("Words_${language.code}.db")

fun Any.phrasesDB(language: Language): File = resource("Phrases_${language.code}.db")

fun Any.resource(name: String): File {
    val classLoader = this.javaClass.classLoader
    val resource = requireNotNull(classLoader.getResource(name))
    return File(resource.file)
}

enum class Language(val code: Int) {
    PL(16), EN(46);
}
