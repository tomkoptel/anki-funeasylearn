package com.olderwold.anki.funeasylearn

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class AnkiCardTest {
    @Test
    fun supports_up_to_5_images() {
        val card = AnkiCard(front = "f", back = "b", explanation = "e", images = listOf("1", "2", "3", "4", "5", "6"))
        card.toList() shouldBeEqualTo listOf("f", "b", "e", "1", "2", "3", "4", "5")
    }

    @Test
    fun if_not_5_images_fills_gaps_with_empty_strings() {
        val card = AnkiCard(front = "f", back = "b", explanation = "e", images = listOf("1"))
        card.toList() shouldBeEqualTo listOf("f", "b", "e", "1", "", "", "", "")
    }

    @Test
    fun if_no_images_fills_empty_spots_with_empty_strings() {
        val card = AnkiCard(front = "f", back = "b", explanation = "e", images = emptyList())
        card.toList() shouldBeEqualTo listOf("f", "b", "e", "", "", "", "", "")
    }
}
