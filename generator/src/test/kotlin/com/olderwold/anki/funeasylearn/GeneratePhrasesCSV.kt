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

    private fun generateWords(start: Int, end: Int, language: Language = Language.PL) {
        val table: CSVTable = phraseGenerator.generate(start, end, language)
        println(table.path)
    }
}
