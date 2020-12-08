package com.olderwold.anki.funeasylearn

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File

class CSVTable(private val path: File) {
    private val rows = mutableListOf<List<String?>>()

    fun append(card: AnkiCard): CSVTable {
        rows.add(card.toList())
        return this
    }

    fun write() {
        csvWriter { delimiter = ',' }.writeAll(rows, path)
    }
}
