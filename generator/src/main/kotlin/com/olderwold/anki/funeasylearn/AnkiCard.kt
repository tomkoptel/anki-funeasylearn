package com.olderwold.anki.funeasylearn

data class AnkiCard(
    val front: String,
    val back: String,
    val explanation: String,
    val imageUrl: String? = null
) {
    fun toList(): List<String?> {
        val front = if (imageUrl == null) {
            "<div>$front</div>"
        } else {
            """<div>$front</div><br><img src="https://http.cat/100"/>"""
        }

        return listOf(front, back, explanation)
    }
}
