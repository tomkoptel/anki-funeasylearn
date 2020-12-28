package com.olderwold.anki.funeasylearn

import com.olderwold.anki.funeasylearn.data.SearchResultDto
import com.olderwold.anki.funeasylearn.generator.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun ShutterStockApi.searchIllustration(query: String): SearchResultDto {
    return search(query, "illustration")
}

fun ShutterStockApi.searchPhoto(query: String): SearchResultDto {
    return search(query, "photo")
}

interface ShutterStockApi {
    fun search(query: String, imageType: String): SearchResultDto

    companion object {
        operator fun invoke(clientBuilder: OkHttpClient.Builder.() -> Unit = {}): ShutterStockApi {
            val api = Retrofit.Builder()
                .baseUrl("https://api.shutterstock.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(AuthenticationInterceptor)
                        .apply(clientBuilder)
                        .build()
                )
                .build()
                .create(Api::class.java)
            return Impl(api)
        }
    }

    private class Impl(
        private val api: Api
    ) : ShutterStockApi {
        override fun search(query: String, imageType: String): SearchResultDto {
            val response = api.search(query, imageType).execute()
            if (response.isSuccessful) {
                return checkNotNull(response.body()) { "API returns empty response" }
            } else {
                throw HttpException(response)
            }
        }
    }

    private interface Api {
        @GET("/v2/images/search")
        fun search(
            @Query("query") query: String,
            @Query("image_type") imageType: String
        ): Call<SearchResultDto>
    }

    private object AuthenticationInterceptor : Interceptor {
        private val credentials = Credentials.basic(
            BuildConfig.SHUTTER_STOCK_CONSUMER_KEY,
            BuildConfig.SHUTTER_STOCK_CONSUMER_SECRET
        )

        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.request().newBuilder()
                .header("Authorization", credentials)
                .build()
                .let { chain.proceed(it) }
        }
    }
}
