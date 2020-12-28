package com.olderwold.anki.funeasylearn

class WordsGenerator(
    private val csvTableFactory: CSVTableFactory,
    private val languageTable: LanguageTable,
    private val api: ShutterStockApi
) {
    @Suppress("NestedBlockDepth", "TooGenericExceptionCaught", "LoopWithTooManyJumpStatements")
    fun generate(start: Int, end: Int, language: Language = Language.PL): CSVTable {
        val plWordsQueries = languageTable.wordsQueries(language)
        val enWordsQueries = languageTable.wordsQueries(Language.EN)
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

        return csvTable
    }
}
