package com.olderwold.anki.funeasylearn

import com.olderwold.anki.funeasylearn.fel.FelWordsDB
import com.olderwold.anki.funeasylearn.words.WordsDb
import okreplay.OkReplay
import okreplay.OkReplayConfig
import okreplay.OkReplayInterceptor
import okreplay.RecorderRule
import okreplay.TapeMode
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.File

@Suppress("LoopWithTooManyJumpStatements")
class GenerateWordsCSV {
    private val okReplayInterceptor = OkReplayInterceptor()
    private val configuration = OkReplayConfig.Builder()
        .defaultMode(TapeMode.READ_ONLY)
        .sslEnabled(true)
        .interceptor(okReplayInterceptor)
        .build()
    private val api = ShutterStockApi { addInterceptor(okReplayInterceptor) }

    @JvmField
    @Rule
    val testRule: TestRule = RecorderRule(configuration)

    @Test
    @OkReplay(tape = "generate_first_100")
    fun generate_first_100_hu() {
        generateWords(start = 1, end = 100, language = Language.HU)
    }

    @Test
    @OkReplay
    fun generate_first_100() {
        generateWords(start = 1, end = 100)
    }

    @Test
    @OkReplay
    fun generate_101_200() {
        generateWords(start = 101, end = 200)
    }

    @Test
    @OkReplay
    fun generate_201_300() {
        generateWords(start = 201, end = 300)
    }

    @Test
    @OkReplay
    fun generate_301_401() {
        generateWords(start = 301, end = 401)
    }

    @Test
    @OkReplay
    fun generate_402_502() {
        generateWords(start = 402, end = 502)
    }

    @Test
    @OkReplay
    fun generate_502_700() {
        generateWords(start = 502, end = 700)
    }

    @Test
    @OkReplay
    fun generate_701_800() {
        generateWords(start = 700, end = 800)
    }

    @Test
    @OkReplay
    fun generate_800_900() {
        generateWords(start = 800, end = 900)
    }

    @Test
    @OkReplay
    fun generate_900_1000() {
        generateWords(start = 900, end = 1000)
    }

    @Test
    @OkReplay
    fun generate_1000_1100() {
        generateWords(start = 1000, end = 1100)
    }

    private fun generateWords(start: Int, end: Int, language: Language = Language.PL) {
        val buildDir = resource(".")
        val plWordsQueries = WordsDb(wordsDB(language).driver()).wordsQueries
        val enWordsQueries = WordsDb(wordsDB(Language.EN).driver()).wordsQueries
        val felWordsQueries = FelWordsDB(resource("FEL_Words.db").driver()).felQueries

        val csvFile = File(buildDir, "anki_words_${start}_$end.csv")
        val csvTable = CSVTable(csvFile)

        val plWords = plWordsQueries.selectAll().executeAsList()

        val startIndex = 0.coerceAtLeast(start - 1)
        val endIndex = (end - 1).coerceAtMost(plWords.size - 1)

        var index = 0
        for (plWordTuple in plWords) {
            if (index < startIndex) {
                index++
                continue
            }
            if (index > endIndex) break

            try {
                val plWord = plWordTuple.LanguageTranslation
                val enWordTuple = enWordsQueries.findById(plWordTuple.WordID).executeAsList().firstOrNull()
                val enWord = enWordTuple?.LanguageTranslation
                val felWordTuple = felWordsQueries.findById(plWordTuple.WordID).executeAsList().firstOrNull()
                val meaning = felWordTuple?.Meaning

                if (plWord != null && enWord != null && meaning != null) {
                    val illustrations = api.searchPhoto(enWord).images(AnkiCard.MAX_IMAGES)
                    val images = if (illustrations.isEmpty()) {
                        api.searchIllustration(enWord).images(AnkiCard.MAX_IMAGES)
                    } else {
                        illustrations
                    }

                    val card = AnkiCard(
                        front = enWord,
                        back = plWord,
                        explanation = meaning,
                        images = images
                    )
                    csvTable.append(card)
                }

            } catch (ex: Exception) {
                println(ex)
            }

            index++
        }

        csvTable.write()
        println(csvFile)
    }
}
