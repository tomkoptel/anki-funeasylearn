package com.olderwold.anki.funeasylearn

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Test
import java.io.File

class ConsumeDBTest {
    @Test
    fun consume_pl() {
        wordsDB(Language.PL).loadAllWords().shouldNotBeEmpty()
    }

    @Test
    fun consume_en() {
        wordsDB(Language.EN).loadAllWords().shouldNotBeEmpty()
    }

    private fun File.loadAllWords(): List<WordTranslations> {
        val driver = JdbcSqliteDriver("jdbc:sqlite:${this.absolutePath}")
        return WordsDb(driver).wordsQueries.selectAll().executeAsList()
    }
}
