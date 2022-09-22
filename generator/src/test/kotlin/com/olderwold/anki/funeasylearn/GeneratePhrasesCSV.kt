package com.olderwold.anki.funeasylearn

import java.io.File
import java.nio.file.Files
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
        generatePhrases(start = 1, end = 100)
    }

    @Test
    @OkReplay
    fun phrases_hu_1_10() {
        generatePhrases(start = 1, end = 10, targetLanguage = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_11_20() {
        generatePhrases(start = 11, end = 20, targetLanguage = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_21_30() {
        generatePhrases(start = 21, end = 30, targetLanguage = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_31_40() {
        generatePhrases(start = 31, end = 40, targetLanguage = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_41_50() {
        generatePhrases(start = 41, end = 50, targetLanguage = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_51_60() {
        generatePhrases(start = 51, end = 60, targetLanguage = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_61_70() {
        generatePhrases(start = 61, end = 70, targetLanguage = Language.HU)
    }

    @Test
    @OkReplay
    fun phrases_hu_71_80() {
        generatePhrases(start = 71, end = 80, targetLanguage = Language.HU)
    }

    private fun generatePhrases(
        start: Int,
        end: Int,
        targetLanguage: Language = Language.PL,
        assistingLanguage: Language = Language.EN
    ) {
        val table: CSVTable =
            phraseGenerator.generate(start, end, targetLanguage, assistingLanguage)
        val desktop = File("${System.getProperty("user.home")}${File.separator}Desktop")
        val srcPath = table.path.toPath()
        if (desktop.exists()) {
            val destPath = File(desktop, table.path.name).toPath()
            Files.copy(srcPath, destPath)
        }
        println(srcPath)
    }
}
