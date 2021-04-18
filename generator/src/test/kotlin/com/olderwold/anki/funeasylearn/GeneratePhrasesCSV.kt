package com.olderwold.anki.funeasylearn

import okreplay.OkReplay
import okreplay.OkReplayConfig
import okreplay.OkReplayInterceptor
import okreplay.RecorderRule
import okreplay.TapeMode
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GeneratePhrasesCSV {
    private val okReplayInterceptor = OkReplayInterceptor()
    private val configuration = OkReplayConfig.Builder()
        .defaultMode(TapeMode.READ_ONLY)
        .sslEnabled(true)
        .interceptor(okReplayInterceptor)
        .build()
    private val api = ShutterStockApi { addInterceptor(okReplayInterceptor) }
    private val phraseGenerator by lazy {
        PhraseGenerator(
            csvTableFactory = CSVTableFactory(buildDir = resource("."), prefix = "phrases"),
            languageTable = phrasesLanguageTable(),
            api = api
        )
    }

    @JvmField
    @Rule
    val testRule: TestRule = RecorderRule(configuration)

    @Test
    @OkReplay
    fun phrases_pl_1_100() {
        generateWords(start = 1, end = 100)
    }

    @Test
    @OkReplay
    fun phrases_hu_1_10() {
        generateWords(start = 1, end = 10, language = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_11_20() {
        generateWords(start = 11, end = 20, language = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_21_30() {
        generateWords(start = 21, end = 30, language = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_31_40() {
        generateWords(start = 31, end = 40, language = Language.HU)
    }

    private fun generateWords(start: Int, end: Int, language: Language = Language.PL) {
        val table: CSVTable = phraseGenerator.generate(start, end, language)
        println(table.path)
    }
}
