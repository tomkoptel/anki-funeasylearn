package com.olderwold.anki.funeasylearn

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.olderwold.anki.funeasylearn.data.SearchResultDto
import com.olderwold.anki.funeasylearn.fel.FelQueries
import com.olderwold.anki.funeasylearn.fel.Words
import com.olderwold.anki.funeasylearn.words.WordTranslations
import com.olderwold.anki.funeasylearn.words.WordsQueries
import com.squareup.sqldelight.Query
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.mockito.Answers

class WordsGeneratorTest {
    private val plWordsQueries = mock<WordsQueries>(name = "plWordsQueries", defaultAnswer = Answers.RETURNS_DEEP_STUBS)
    private val enWordsQueries = mock<WordsQueries>(name = "enWordsQueries", defaultAnswer = Answers.RETURNS_DEEP_STUBS)
    private val felWordsQueries = mock<FelQueries>(name = "felWordsQueries", defaultAnswer = Answers.RETURNS_DEEP_STUBS)

    private val csvTable = mock<CSVTable>()
    private val csvTableFactory = mock<CSVTableFactory> {
        given(mock.create(any(), any())).willReturn(csvTable)
    }
    private val languageTable = mock<LanguageTable> {
        given(mock.wordsQueries(Language.PL)).willReturn(plWordsQueries)
        given(mock.wordsQueries(Language.EN)).willReturn(enWordsQueries)
        given(mock.felQueries()).willReturn(felWordsQueries)
    }
    private val dummyImages = listOf("image1", "image2")
    private val dto = mock<SearchResultDto> {
        given(mock.images(any())).willReturn(dummyImages)
    }
    private val api: ShutterStockApi = mock {
        given(mock.search(any(), eq("photo"))).willReturn(dto)
    }
    private val wordsGenerator = WordsGenerator(
        csvTableFactory = csvTableFactory,
        languageTable = languageTable,
        api = api
    )

    @Test
    fun create_1_to_5() {
        val plWords = generateWords(num = 20, prefix = "PL")
        val enWords = generateWords(num = 20, prefix = "EN")
        val resultTable = csvTable.appendAggregates()

        plWordsQueries selectAllReturns plWords
        enWordsQueries selectAllReturns enWords
        enWordsQueries.findByIdAlwaysReturnsTranslation()
        felWordsQueries.findByIdAlwaysReturnsMeaning()

        wordsGenerator.generate(start = 1, end = 5)

        resultTable.size shouldBeEqualTo 5
        verify(csvTable).append(ankiCard(index = 1))
        verify(csvTable).append(ankiCard(index = 2))
        verify(csvTable).append(ankiCard(index = 3))
        verify(csvTable).append(ankiCard(index = 4))
        verify(csvTable).append(ankiCard(index = 5))
    }

    @Test
    fun create_6_to_10() {
        val plWords = generateWords(num = 20, prefix = "PL")
        val enWords = generateWords(num = 20, prefix = "EN")
        val resultTable = csvTable.appendAggregates()

        plWordsQueries selectAllReturns plWords
        enWordsQueries selectAllReturns enWords
        enWordsQueries.findByIdAlwaysReturnsTranslation()
        felWordsQueries.findByIdAlwaysReturnsMeaning()

        wordsGenerator.generate(start = 6, end = 10)

        resultTable.size shouldBeEqualTo 5
        verify(csvTable).append(ankiCard(index = 6))
        verify(csvTable).append(ankiCard(index = 7))
        verify(csvTable).append(ankiCard(index = 8))
        verify(csvTable).append(ankiCard(index = 9))
        verify(csvTable).append(ankiCard(index = 10))
    }

    @Test
    fun create_1_to_15() {
        val plWords = generateWords(num = 20, prefix = "PL")
        val enWords = generateWords(num = 20, prefix = "EN")
        val resultTable = csvTable.appendAggregates()

        plWordsQueries selectAllReturns plWords
        enWordsQueries selectAllReturns enWords
        enWordsQueries.findByIdAlwaysReturnsTranslation()
        felWordsQueries.findByIdAlwaysReturnsMeaning()

        wordsGenerator.generate(start = 1, end = 15)

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

    private fun generateWords(num: Int, prefix: String): List<WordTranslations> {
        return (1..num).map { id -> wordTranslations(prefix, id) }
    }

    private fun wordTranslations(
        prefix: String,
        id: Int
    ): WordTranslations {
        val translation = "${prefix}_word_$id"
        return mock(name = translation) {
            given(mock.WordID).willReturn(id.toLong())
            given(mock.LanguageTranslation).willReturn(translation)
        }
    }

    private infix fun WordsQueries.selectAllReturns(words: List<WordTranslations>) {
        given(selectAll().executeAsList()).willReturn(words)
    }

    private fun WordsQueries.findByIdAlwaysReturnsTranslation() {
        given(findById(any())).willAnswer { invocationOnMock ->
            val wordId = invocationOnMock.arguments.first() as Long
            val words = listOf(wordTranslations("EN", wordId.toInt()))

            mock<Query<WordTranslations>> {
                given(mock.executeAsList()).willReturn(words)
            }
        }
    }

    private fun FelQueries.findByIdAlwaysReturnsMeaning() {
        given(findById(any())).willAnswer { invocationOnMock ->
            val wordId = invocationOnMock.arguments.first() as Long
            val words = mock<Words>(name = "word_explanation_$wordId") {
                given(mock.Meaning).willReturn("Meaning $wordId")
            }

            mock<Query<Words>> {
                given(mock.executeAsList()).willReturn(listOf(words))
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
        front = "EN_word_$index",
        back = "PL_word_$index",
        explanation = "Meaning $index",
        images = dummyImages
    )
}
