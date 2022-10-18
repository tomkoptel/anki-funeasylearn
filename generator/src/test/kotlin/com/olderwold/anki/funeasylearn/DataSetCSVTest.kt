package com.olderwold.anki.funeasylearn

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Test

class DataSetCSVTest {
    private val expectedUsers = setOf<String>()
    private val budapest = TimeZone.getTimeZone("Europe/Budapest")

    @Test
    fun parse() {
        val file = resource("data-set.json")
        val fileReader = FileReader(file)
        val dataSet = Gson().fromJson(fileReader, DataSet::class.java)
        dataSet.shouldNotBeEmpty()

        val byUser = mutableMapOf<String, MutableList<DataSet.DataSetItem>>()
        dataSet.forEach { dataSetItem ->
            val user = dataSetItem.user
            if (user != null) {
                val list = byUser[user]
                if (list == null) {
                    byUser[user] = mutableListOf(dataSetItem)
                } else {
                    list.add(dataSetItem)
                }
            }
        }

        val ctwData = File(resource("."), "ctwData")
        ctwData.mkdirs()

        byUser.forEach { (user, items) ->
            if (expectedUsers.contains(user.lowercase())) {
                val userDir = File(ctwData, user)
                userDir.mkdirs()
                items.sortBy { it.ts ?: 0.0 }

                val byDate = mutableMapOf<LocalDate, MutableList<DataSet.DataSetItem>>()
                items.forEach {
                    val timeStamp = it.ts?.toLong()
                    if (timeStamp != null) {
                        val date = LocalDate.ofInstant(Instant.ofEpochMilli(timeStamp), budapest.toZoneId())
                        if (date.month == Month.MAY) {
                            val list = byDate[date]
                            if (list == null) {
                                byDate[date] = mutableListOf(it)
                            } else {
                                list.add(it)
                            }
                        }
                    }
                }

                byDate.forEach { (date, itemsByDate) ->
                    val dateAsString = date.format(DateTimeFormatter.ISO_DATE)
                    val csvFile = File(userDir, "$dateAsString.csv")
                    csvFile.writeItems(itemsByDate)
                }

                if (userDir.listFiles().orEmpty().isEmpty()) {
                    userDir.delete()
                }
            }
        }

        println("========${ctwData.listFiles().orEmpty().size}")
    }

    private fun File.writeItems(items: List<DataSet.DataSetItem>) {
        val csvWriter = csvWriter { delimiter = ',' }
        val rows = mutableListOf<List<String?>>(
            listOf("user", "device", "exercise", "timeStamp", "edaValue")
        )
        items.forEach { item ->
            item.data?.eda?.forEach { edaItem ->
                val timeStamp = edaItem.firstOrNull()
                val edaValue = edaItem.lastOrNull()
                if (timeStamp != null && edaValue != null) {
                    val dateTime =
                        ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeStamp.toLong()), budapest.toZoneId())
                    val row = listOf(
                        item.user,
                        item.device,
                        item.exercise,
                        dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        "$edaValue"
                    )
                    rows.add(row)
                }
            }
            if (rows.size > 1) {
                csvWriter.writeAll(rows, this)
            } else {
                this.delete()
            }
        }
    }
}
