package com.olderwold.anki.funeasylearn

import java.io.File

interface DbFileProvider {
    fun provide(language: Language): File
}
