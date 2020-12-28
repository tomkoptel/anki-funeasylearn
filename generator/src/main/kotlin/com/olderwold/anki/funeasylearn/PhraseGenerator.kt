package com.olderwold.anki.funeasylearn

class PhraseGenerator(
    private val csvTableFactory: CSVTableFactory,
    private val languageTable: LanguageTable,
    private val api: ShutterStockApi
) {
    @Suppress("NestedBlockDepth", "TooGenericExceptionCaught", "LoopWithTooManyJumpStatements")
    fun generate(start: Int, end: Int, language: Language): CSVTable {
        val plWordsQueries = languageTable.phraseQueries(language)
        val enWordsQueries = languageTable.phraseQueries(Language.EN)
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
                val enWordTuple = enWordsQueries.findById(plWordTuple.PhraseID).executeAsList().firstOrNull()
                val enWord = enWordTuple?.LanguageTranslation

                if (plWord != null && enWord != null) {
                    val images = api.searchOrDefault(enWord)
                    val card = AnkiCard(
                        front = enWord,
                        back = plWord,
                        explanation = "",
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
