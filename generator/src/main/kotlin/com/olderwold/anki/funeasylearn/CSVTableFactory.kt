package com.olderwold.anki.funeasylearn

import java.io.File

class CSVTableFactory(
    private val buildDir: File
) {
    fun create(start: Int, end: Int): CSVTable {
        val csvFile = File(buildDir, "anki_words_${start}_$end.csv")
        return CSVTable(csvFile)
    }
}
