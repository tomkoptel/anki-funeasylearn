package com.olderwold.anki.funeasylearn

import java.io.File

fun Any.wordsDB(language: Language): File = db("Words", language)

fun Any.phrasesDB(language: Language): File = db("Phrases", language)

fun Any.db(prefix: String, language: Language): File {
    val classLoader = this.javaClass.classLoader
    val resource = requireNotNull(classLoader.getResource("${prefix}_${language.code}.db"))
    return File(resource.file)
}

enum class Language(val code: Int) {
    PL(16), EN(46);
}
