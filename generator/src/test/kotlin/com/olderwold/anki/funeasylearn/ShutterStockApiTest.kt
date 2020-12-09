package com.olderwold.anki.funeasylearn

import okreplay.OkReplay
import okreplay.OkReplayConfig
import okreplay.OkReplayInterceptor
import okreplay.RecorderRule
import okreplay.TapeMode
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class ShutterStockApiTest {
    private val okReplayInterceptor = OkReplayInterceptor()
    private val configuration = OkReplayConfig.Builder()
        .defaultMode(TapeMode.READ_ONLY)
        .sslEnabled(true)
        .interceptor(okReplayInterceptor)
        .build()
    private val api = ShutterStockApi { addInterceptor(okReplayInterceptor) }

    @JvmField
    @Rule
    val testRule: TestRule = RecorderRule(configuration)

    @Test
    @OkReplay
    fun test_search() {
        val result = api.search(query = "modest", image_type = "photo")
        result.searchId!! shouldBeEqualTo "547bcaaa-1d9f-456a-ab7c-12603e21079d"
    }
}
