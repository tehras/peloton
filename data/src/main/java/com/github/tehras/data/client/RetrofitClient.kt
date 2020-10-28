package com.github.tehras.data.client

import com.github.tehras.data.PelotonApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object RetrofitClient {
    // The base URL where our API is
    private const val BASE_URL = "https://api.onepeloton.com/"

    private val contentType = MediaType.parse("application/json")!!

    @ExperimentalSerializationApi
    private val authRetrofit = Retrofit.Builder()
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
        .baseUrl(BASE_URL)
        .build()

    @ExperimentalSerializationApi
    val pelotonApi: PelotonApi = authRetrofit.create(PelotonApi::class.java)
}