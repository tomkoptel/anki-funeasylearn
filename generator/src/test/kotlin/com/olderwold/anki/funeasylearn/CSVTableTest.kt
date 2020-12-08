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
            imageUrl = "https://http.cat/100",
            explanation = "bad person"
        )
        CSVTable(csvFile).append(card).write()

        val rows = csvReader().readAll(csvFile).flatten()
        rows shouldContain """<div>bad</div><br><img src="https://http.cat/100"/>"""
        rows shouldContain "zły"
        rows shouldContain "bad person"
    }
}
