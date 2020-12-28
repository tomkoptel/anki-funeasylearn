package com.olderwold.anki.funeasylearn

import com.olderwold.anki.funeasylearn.fel.FelQueries
import com.olderwold.anki.funeasylearn.fel.FelWordsDB
import com.olderwold.anki.funeasylearn.phrases.PhrasesDb
import com.olderwold.anki.funeasylearn.phrases.PhrasesQueries
import com.olderwold.anki.funeasylearn.words.WordsDb
import com.olderwold.anki.funeasylearn.words.WordsQueries
import java.io.File

class LanguageTable(
    private val dbFileProvider: DbFileProvider,
    private val felWordsFile: File
) {
    fun wordsQueries(language: Language): WordsQueries {
        val driver = dbFileProvider.provide(language).driver()
        return WordsDb(driver).wordsQueries
    }

    fun phraseQueries(language: Language): PhrasesQueries {
        val driver = dbFileProvider.provide(language).driver()
        return PhrasesDb(driver).phrasesQueries
    }

    fun felQueries(): FelQueries {
        return FelWordsDB(felWordsFile.driver()).felQueries
    }
}
