package com.olderwold.anki.funeasylearn

import com.olderwold.anki.funeasylearn.fel.FelWordsDB
import com.olderwold.anki.funeasylearn.phrases.PhrasesDb
import com.olderwold.anki.funeasylearn.words.WordsDb
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Test

class DbIntegrationTest {
    @Test
    fun consume_words_pl() {
        WordsDb(wordsDB(Language.PL).driver()).wordsQueries.selectAll().executeAsList().shouldNotBeEmpty()
    }

    @Test
    fun consume_words_en() {
        WordsDb(wordsDB(Language.EN).driver()).wordsQueries.selectAll().executeAsList().shouldNotBeEmpty()
    }

    @Test
    fun consume_phrases_pl() {
        PhrasesDb(phrasesDB(Language.PL).driver()).phrasesQueries.selectAll().executeAsList().shouldNotBeEmpty()
    }

    @Test
    fun consume_phrases_en() {
        PhrasesDb(phrasesDB(Language.EN).driver()).phrasesQueries.selectAll().executeAsList().shouldNotBeEmpty()
    }

    @Test
    fun fel_words() {
        FelWordsDB(resource("FEL_Words.db").driver()).felQueries.selectAll().executeAsList().shouldNotBeEmpty()
    }
}
