package com.github.tehras.data.di

import com.github.tehras.data.AuthApi
import com.github.tehras.data.PelotonApi
import com.github.tehras.data.auth.AuthRepo
import com.github.tehras.data.auth.RealAuthRepo
import com.github.tehras.data.client.RetrofitClient
import com.github.tehras.data.client.RetrofitClient.AuthQualifier
import com.github.tehras.data.client.RetrofitClient.UnauthQualifier
import com.github.tehras.data.user.RealUserRepo
import com.github.tehras.data.user.UserRepo
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>(AuthQualifier).create(PelotonApi::class.java) }
    single { get<Retrofit>(UnauthQualifier).create(AuthApi::class.java) }

    single<AuthRepo> { RealAuthRepo(get(), get()) }
    factory<UserRepo> { RealUserRepo(get(), get()) }
}

@ExperimentalSerializationApi
val retrofitModule = module {
    single(qualifier = AuthQualifier) {
        RetrofitClient.createAuthClient(get())
    }
    single(qualifier = UnauthQualifier) {
        RetrofitClient.createUnauthClient()
    }
}