package com.olderwold.anki.funeasylearn

class WordsGenerator(
    private val csvTableFactory: CSVTableFactory,
    private val languageTable: LanguageTable,
    private val api: ShutterStockApi
) {
    @Suppress("NestedBlockDepth", "TooGenericExceptionCaught", "LoopWithTooManyJumpStatements")
    fun generate(
        start: Int,
        end: Int,
        targetLanguage: Language = Language.PL,
        assistingLanguage: Language = Language.EN,
    ): CSVTable {
        val plWordsQueries = languageTable.wordsQueries(targetLanguage)
        val enWordsQueries = languageTable.wordsQueries(assistingLanguage)
        val felWordsQueries = languageTable.felQueries()
        val csvTable = csvTableFactory.create(start, end)
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
                    val images = api.searchOrDefault(enWord)
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

        return csvTable
    }
}
