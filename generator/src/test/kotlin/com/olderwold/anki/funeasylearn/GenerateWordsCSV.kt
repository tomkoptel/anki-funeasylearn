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
    fun words_hu_41_60() {
        generateWords(start = 41, end = 60, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_61_80() {
        generateWords(start = 61, end = 80, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_81_100() {
        generateWords(start = 81, end = 100, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_101_120() {
        generateWords(start = 101, end = 120, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_121_140() {
        generateWords(start = 121, end = 140, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_141_160() {
        generateWords(start = 141, end = 160, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_161_180() {
        generateWords(start = 161, end = 180, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_181_200() {
        generateWords(start = 181, end = 200, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_200_220() {
        generateWords(start = 200, end = 220, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_221_240() {
        generateWords(start = 221, end = 240, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_241_260() {
        generateWords(start = 241, end = 260, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_261_280() {
        generateWords(start = 261, end = 280, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_281_300() {
        generateWords(start = 281, end = 300, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_301_320() {
        generateWords(start = 301, end = 320, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_321_340() {
        generateWords(start = 321, end = 340, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_341_360() {
        generateWords(start = 341, end = 360, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_361_380() {
        generateWords(start = 361, end = 380, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_381_400() {
        generateWords(start = 381, end = 400, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_400_420() {
        generateWords(start = 400, end = 420, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_421_440() {
        generateWords(start = 421, end = 440, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_441_460() {
        generateWords(start = 441, end = 460, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_461_480() {
        generateWords(start = 461, end = 480, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_481_500() {
        generateWords(start = 481, end = 500, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_501_520() {
        generateWords(start = 501, end = 520, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_521_540() {
        generateWords(start = 521, end = 540, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_541_560() {
        generateWords(start = 541, end = 560, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_561_580() {
        generateWords(start = 561, end = 580, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_581_600() {
        generateWords(start = 581, end = 600, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_600_620() {
        generateWords(start = 600, end = 620, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_hu_620_640() {
        generateWords(start = 620, end = 640, language = Language.HU)
    }

    @Test
    @OkReplay
    fun words_pl_1_100() {
        generateWords(start = 1, end = 100, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_101_200() {
        generateWords(start = 101, end = 200, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_201_300() {
        generateWords(start = 201, end = 300, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_301_401() {
        generateWords(start = 301, end = 401, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_402_502() {
        generateWords(start = 402, end = 502, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_502_700() {
        generateWords(start = 502, end = 700, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_701_800() {
        generateWords(start = 700, end = 800, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_800_900() {
        generateWords(start = 800, end = 900, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_900_1000() {
        generateWords(start = 900, end = 1000, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1000_1100() {
        generateWords(start = 1000, end = 1100, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1101_1200() {
        generateWords(start = 1101, end = 1200, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1201_1300() {
        generateWords(start = 1201, end = 1300, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1301_1400() {
        generateWords(start = 1301, end = 1400, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1401_1500() {
        generateWords(start = 1401, end = 1500, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1501_1600() {
        generateWords(start = 1501, end = 1600, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1601_1700() {
        generateWords(start = 1601, end = 1700, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1701_1800() {
        generateWords(start = 1701, end = 1800, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1801_1900() {
        generateWords(start = 1801, end = 1900, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_1901_2000() {
        generateWords(start = 1901, end = 2000, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2001_2100() {
        generateWords(start = 2001, end = 2100, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2101_2200() {
        generateWords(start = 2101, end = 2200, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2201_2300() {
        generateWords(start = 2201, end = 2300, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2301_2400() {
        generateWords(start = 2301, end = 2400, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2401_2500() {
        generateWords(start = 2401, end = 2500, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2501_2600() {
        generateWords(start = 2501, end = 2600, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2601_2700() {
        generateWords(start = 2601, end = 2700, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2701_2800() {
        generateWords(start = 2701, end = 2800, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2801_2900() {
        generateWords(start = 2801, end = 2900, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_2901_3000() {
        generateWords(start = 2901, end = 3000, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_3001_3100() {
        generateWords(start = 3001, end = 3100, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_3101_3150() {
        generateWords(start = 3101, end = 3150, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_3151_3200() {
        generateWords(start = 3151, end = 3200, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_3201_3250() {
        generateWords(start = 3201, end = 3250, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_3251_3300() {
        generateWords(start = 3251, end = 3300, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_3301_3350() {
        generateWords(start = 3301, end = 3350, language = Language.PL)
    }

    @Test
    @OkReplay
    fun words_pl_3351_3400() {
        generateWords(start = 3351, end = 3400, language = Language.PL)
    }

    private fun generateWords(start: Int, end: Int, language: Language) {
        val table = wordsGenerator.generate(start, end, language)
        val desktop = File("${System.getProperty("user.home")}${File.separator}Desktop")
        val srcPath = table.path.toPath()
        if (desktop.exists()) {
            val destPath = File(desktop, table.path.name).toPath()
            Files.copy(srcPath, destPath)
        }
        println(srcPath)
    }
}
