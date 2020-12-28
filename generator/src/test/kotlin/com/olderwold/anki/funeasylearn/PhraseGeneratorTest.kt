package com.olderwold.anki.funeasylearn

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.olderwold.anki.funeasylearn.data.SearchResultDto
import com.olderwold.anki.funeasylearn.phrases.PhraseTranslations
import com.olderwold.anki.funeasylearn.phrases.PhrasesQueries
import com.squareup.sqldelight.Query
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.mockito.Answers

class PhraseGeneratorTest {
    private val plWordsQueries = mock<PhrasesQueries>(name = "plWordsQueries", defaultAnswer = Answers.RETURNS_DEEP_STUBS)
    private val enWordsQueries = mock<PhrasesQueries>(name = "enWordsQueries", defaultAnswer = Answers.RETURNS_DEEP_STUBS)

    private val csvTable = mock<CSVTable>()
    private val csvTableFactory = mock<CSVTableFactory> {
        given(mock.create(any(), any())).willReturn(csvTable)
    }
    private val languageTable = mock<LanguageTable> {
        given(mock.phraseQueries(Language.PL)).willReturn(plWordsQueries)
        given(mock.phraseQueries(Language.EN)).willReturn(enWordsQueries)
    }
    private val dummyImages = listOf("image1", "image2")
    private val dto = mock<SearchResultDto> {
        given(mock.images(any())).willReturn(dummyImages)
    }
    private val api: ShutterStockApi = mock {
        given(mock.search(any(), eq("photo"))).willReturn(dto)
    }
    private val wordsGenerator = PhraseGenerator(
        csvTableFactory = csvTableFactory,
        languageTable = languageTable,
        api = api
    )

    @Test
    fun create_1_to_5() {
        val plWords = generatePhrases(num = 20, prefix = "PL")
        val enWords = generatePhrases(num = 20, prefix = "EN")
        val resultTable = csvTable.appendAggregates()

        plWordsQueries selectAllReturns plWords
        enWordsQueries selectAllReturns enWords
        enWordsQueries.findByIdAlwaysReturnsTranslation()

        wordsGenerator.generate(start = 1, end = 5, language = Language.PL)

        resultTable.size shouldBeEqualTo 5
        verify(csvTable).append(ankiCard(index = 1))
        verify(csvTable).append(ankiCard(index = 2))
        verify(csvTable).append(ankiCard(index = 3))
        verify(csvTable).append(ankiCard(index = 4))
        verify(csvTable).append(ankiCard(index = 5))
    }

    @Test
    fun create_6_to_10() {
        val plWords = generatePhrases(num = 20, prefix = "PL")
        val enWords = generatePhrases(num = 20, prefix = "EN")
        val resultTable = csvTable.appendAggregates()

        plWordsQueries selectAllReturns plWords
        enWordsQueries selectAllReturns enWords
        enWordsQueries.findByIdAlwaysReturnsTranslation()

        wordsGenerator.generate(start = 6, end = 10, language = Language.PL)

        resultTable.size shouldBeEqualTo 5
        verify(csvTable).append(ankiCard(index = 6))
        verify(csvTable).append(ankiCard(index = 7))
        verify(csvTable).append(ankiCard(index = 8))
        verify(csvTable).append(ankiCard(index = 9))
        verify(csvTable).append(ankiCard(index = 10))
    }

    @Test
    fun create_1_to_15() {
        val plWords = generatePhrases(num = 15, prefix = "PL")
        val enWords = generatePhrases(num = 15, prefix = "EN")
        val resultTable = csvTable.appendAggregates()

        plWordsQueries selectAllReturns plWords
        enWordsQueries selectAllReturns enWords
        enWordsQueries.findByIdAlwaysReturnsTranslation()

        wordsGenerator.generate(start = 1, end = 15, language = Language.PL)

        resultTable.size shouldBeEqualTo 15
        verify(csvTable).append(ankiCard(index = 1))
        verify(csvTable).append(ankiCard(index = 2))
        verify(csvTable).append(ankiCard(index = 3))
        verify(csvTable).append(ankiCard(index = 4))
        verify(csvTable).append(ankiCard(index = 5))
        verify(csvTable).append(ankiCard(index = 6))
        verify(csvTable).append(ankiCard(index = 7))
        verify(csvTable).append(ankiCard(index = 8))
        verify(csvTable).append(ankiCard(index = 9))
        verify(csvTable).append(ankiCard(index = 10))
        verify(csvTable).append(ankiCard(index = 11))
        verify(csvTable).append(ankiCard(index = 12))
        verify(csvTable).append(ankiCard(index = 13))
        verify(csvTable).append(ankiCard(index = 14))
        verify(csvTable).append(ankiCard(index = 15))
    }

    private fun generatePhrases(num: Int, prefix: String): List<PhraseTranslations> {
        return (1..num).map { id -> phraseTranslations(prefix, id) }
    }

    private fun phraseTranslations(
        prefix: String,
        id: Int
    ): PhraseTranslations {
        val translation = "${prefix}_phrase_$id"
        return mock(name = translation) {
            given(mock.PhraseID).willReturn(id.toLong())
            given(mock.LanguageTranslation).willReturn(translation)
        }
    }

    private infix fun PhrasesQueries.selectAllReturns(words: List<PhraseTranslations>) {
        given(selectAll().executeAsList()).willReturn(words)
    }

    private fun PhrasesQueries.findByIdAlwaysReturnsTranslation() {
        given(findById(any())).willAnswer { invocationOnMock ->
            val wordId = invocationOnMock.arguments.first() as Long
            val phrases = listOf(phraseTranslations("EN", wordId.toInt()))

            mock<Query<PhraseTranslations>> {
                given(mock.executeAsList()).willReturn(phrases)
            }
        }
    }

    private fun CSVTable.appendAggregates(): List<AnkiCard> {
        val cards = mutableListOf<AnkiCard>()
        given(append(any())).willAnswer {
            cards.add(it.arguments.first() as AnkiCard)
            Unit
        }
        return cards
    }

    private fun ankiCard(index: Int) = AnkiCard(
        front = "EN_phrase_$index",
        back = "PL_phrase_$index",
        explanation = "",
        images = dummyImages
    )
}
