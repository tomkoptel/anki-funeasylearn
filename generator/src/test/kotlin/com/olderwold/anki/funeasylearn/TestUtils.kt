package com.olderwold.anki.funeasylearn

import java.io.File

fun Any.wordsDB(language: Language): File {
    val classLoader = this.javaClass.classLoader
    val resource = requireNotNull(classLoader.getResource("Words_${language.code}.db"))
    return File(resource.file)
}

enum class Language(val code: Int) {
    PL(16), EN(46);
}
