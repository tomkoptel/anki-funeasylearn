package com.olderwold.anki.funeasylearn

import java.io.File

class CSVTableFactory(
    private val buildDir: File,
    private val prefix: String
) {
    fun create(start: Int, end: Int): CSVTable {
        val csvFile = File(buildDir, "anki_${prefix}_${start}_$end.csv")
        return CSVTable(csvFile)
    }
}
