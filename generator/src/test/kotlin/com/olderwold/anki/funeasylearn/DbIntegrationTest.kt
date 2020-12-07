package com.olderwold.anki.funeasylearn

import com.olderwold.anki.funeasylearn.phrases.PhrasesDb
import com.olderwold.anki.funeasylearn.words.WordsDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Test
import java.io.File

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

    private fun File.driver(): SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${this.absolutePath}")
}
