package com.github.tehras.data.di

import com.github.tehras.data.client.RetrofitClient.pelotonApi
import com.github.tehras.data.overview.RealUserRepo
import com.github.tehras.data.overview.UserRepo
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.dsl.module

@ExperimentalSerializationApi
val overviewModule = module {
    single { pelotonApi }
    single<UserRepo> { RealUserRepo(get()) }
}