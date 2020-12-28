package com.olderwold.anki.funeasylearn

import java.io.File

fun Any.wordsLanguageTable(): LanguageTable {
    return LanguageTable(
        dbFileProvider = object : DbFileProvider {
            override fun provide(language: Language): File {
                return wordsDB(language)
            }
        },
        felWordsFile = resource("FEL_Words.db")
    )
}

fun Any.phrasesLanguageTable(): LanguageTable {
    return LanguageTable(
        dbFileProvider = object : DbFileProvider {
            override fun provide(language: Language): File {
                return phrasesDB(language)
            }
        },
        felWordsFile = resource("FEL_Words.db")
    )
}
