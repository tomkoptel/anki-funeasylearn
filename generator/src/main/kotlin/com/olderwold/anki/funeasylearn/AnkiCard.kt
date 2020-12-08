package com.olderwold.anki.funeasylearn

data class AnkiCard(
    val front: String,
    val back: String,
    val explanation: String,
    val images: List<String>
) {
    companion object {
        const val MAX_IMAGES = 5
    }
    fun toList(): List<String?> {
        val images5 = images.take(MAX_IMAGES).toMutableList()
        if (images5.size < MAX_IMAGES) {
            (images5.size until MAX_IMAGES).forEach {
                images5.add(it, "")
            }
        }
        return listOf(front, back, explanation) + images5
    }
}
