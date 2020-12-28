package com.olderwold.anki.funeasylearn

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.io.File

fun File.driver(): SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${this.absolutePath}")
