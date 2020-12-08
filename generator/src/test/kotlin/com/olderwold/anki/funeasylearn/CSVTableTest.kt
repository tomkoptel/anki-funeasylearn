package com.olderwold.anki.funeasylearn

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import org.amshove.kluent.shouldContain
import org.junit.Test

class CSVTableTest {
    @Test
    fun generate_file() {
        val buildDir = resource(".")
        val csvFile = File(buildDir, "words.csv")
        val card = AnkiCard(
            front = "bad",
            back = "zły",
            images = listOf(
                "https://http.cat/100",
                "https://http.cat/101",
                "https://http.cat/102",
                "https://http.cat/103",
                "https://http.cat/104",
            ),
            explanation = "bad person"
        )
        CSVTable(csvFile).append(card).write()

        val rows = csvReader().readAll(csvFile).flatten()
        rows shouldContain "https://http.cat/100"
        rows shouldContain "https://http.cat/101"
        rows shouldContain "https://http.cat/102"
        rows shouldContain "https://http.cat/103"
        rows shouldContain "https://http.cat/104"
        rows shouldContain "bad"
        rows shouldContain "zły"
        rows shouldContain "bad person"
    }
}
