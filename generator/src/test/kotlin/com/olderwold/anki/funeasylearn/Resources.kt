package com.olderwold.anki.funeasylearn

import java.io.File

fun Any.wordsDB(language: Language): File = db("Words_${language.code}.db")

fun Any.phrasesDB(language: Language): File = db("Phrases_${language.code}.db")

fun Any.db(name: String): File {
    val classLoader = this.javaClass.classLoader
    val resource = requireNotNull(classLoader.getResource(name))
    return File(resource.file)
}

enum class Language(val code: Int) {
    PL(16), EN(46);
}
