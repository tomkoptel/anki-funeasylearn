package com.olderwold.anki.funeasylearn

import okreplay.OkReplay
import okreplay.OkReplayConfig
import okreplay.OkReplayInterceptor
import okreplay.RecorderRule
import okreplay.TapeMode
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GenerateWordsCSV {
    private val okReplayInterceptor = OkReplayInterceptor()
    private val configuration = OkReplayConfig.Builder()
        .defaultMode(TapeMode.READ_ONLY)
        .sslEnabled(true)
        .interceptor(okReplayInterceptor)
        .build()
    private val api = ShutterStockApi { addInterceptor(okReplayInterceptor) }
    private val wordsGenerator by lazy {
        WordsGenerator(
            csvTableFactory = CSVTableFactory(buildDir = resource("."), prefix = "words"),
            languageTable = wordsLanguageTable(),
            api = api
        )
    }

    @JvmField
    @Rule
    val testRule: TestRule = RecorderRule(configuration)

    @Test
    @OkReplay
    fun words_hu_1_20() {
        generateWords(start = 1, end = 20, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_21_40() {
        generateWords(start = 21, end = 40, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_pl_1_100() {
        generateWords(start = 1, end = 100)
    }

    @Test
    @OkReplay
    fun words_pl_101_200() {
        generateWords(start = 101, end = 200)
    }

    @Test
    @OkReplay
    fun words_pl_201_300() {
        generateWords(start = 201, end = 300)
    }

    @Test
    @OkReplay
    fun words_pl_301_401() {
        generateWords(start = 301, end = 401)
    }

    @Test
    @OkReplay
    fun words_pl_402_502() {
        generateWords(start = 402, end = 502)
    }

    @Test
    @OkReplay
    fun words_pl_502_700() {
        generateWords(start = 502, end = 700)
    }

    @Test
    @OkReplay
    fun words_pl_701_800() {
        generateWords(start = 700, end = 800)
    }

    @Test
    @OkReplay
    fun words_pl_800_900() {
        generateWords(start = 800, end = 900)
    }

    @Test
    @OkReplay
    fun words_pl_900_1000() {
        generateWords(start = 900, end = 1000)
    }

    @Test
    @OkReplay
    fun words_pl_1000_1100() {
        generateWords(start = 1000, end = 1100)
    }

    @Test
    @OkReplay
    fun words_pl_1101_1200() {
        generateWords(start = 1101, end = 1200)
    }

    @Test
    @OkReplay
    fun words_pl_1201_1300() {
        generateWords(start = 1201, end = 1300)
    }

    @Test
    @OkReplay
    fun words_pl_1301_1400() {
        generateWords(start = 1301, end = 1400)
    }

    @Test
    @OkReplay
    fun words_pl_1401_1500() {
        generateWords(start = 1401, end = 1500)
    }

    @Test
    @OkReplay
    fun words_pl_1501_1600() {
        generateWords(start = 1501, end = 1600)
    }

    @Test
    @OkReplay
    fun words_pl_1601_1700() {
        generateWords(start = 1601, end = 1700)
    }

    @Test
    @OkReplay
    fun words_pl_1701_1800() {
        generateWords(start = 1701, end = 1800)
    }

    @Test
    @OkReplay
    fun words_pl_1801_1900() {
        generateWords(start = 1801, end = 1900)
    }

    @Test
    @OkReplay
    fun words_pl_1901_2000() {
        generateWords(start = 1901, end = 2000)
    }

    @Test
    @OkReplay
    fun words_pl_2001_2100() {
        generateWords(start = 2001, end = 2100)
    }

    @Test
    @OkReplay
    fun words_pl_2101_2200() {
        generateWords(start = 2101, end = 2200)
    }

    @Test
    @OkReplay
    fun words_pl_2201_2300() {
        generateWords(start = 2201, end = 2300)
    }

    @Test
    @OkReplay
    fun words_pl_2301_2400() {
        generateWords(start = 2301, end = 2400)
    }

    @Test
    @OkReplay
    fun words_pl_2401_2500() {
        generateWords(start = 2401, end = 2500)
    }

    @Test
    @OkReplay
    fun words_pl_2501_2600() {
        generateWords(start = 2501, end = 2600)
    }

    @Test
    @OkReplay
    fun words_pl_2601_2700() {
        generateWords(start = 2601, end = 2700)
    }

    private fun generateWords(start: Int, end: Int, language: Language = Language.PL) {
        val table: CSVTable = wordsGenerator.generate(start, end, language)
        println(table.path)
    }
}
