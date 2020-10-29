package com.github.tehras.data.client

import com.github.tehras.data.auth.AuthRepo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.*
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import retrofit2.Retrofit

object RetrofitClient {
    // The base URL where our API is
    private const val BASE_URL = "https://api.onepeloton.com/"

    private val contentType = MediaType.parse("application/json")!!

    @ExperimentalSerializationApi
    fun createAuthClient(authRepo: AuthRepo): Retrofit {
        val authData = authRepo.authData()!!

        val client = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) = Unit

                override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                    return mutableListOf(
                        Cookie.Builder()
                            .name("peloton_session_id")
                            .value(authData.session_id)
                            .domain("onepeloton.com")
                            .build()
                    )
                }

            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("peloton-platform", "android")
                    .build()

                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
    }

    @ExperimentalSerializationApi
    fun createUnauthClient(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
    }

    object AuthQualifier : Qualifier {
        override val value: QualifierValue = "auth"
    }

    object UnauthQualifier : Qualifier {
        override val value: QualifierValue = "unauth"
    }
}