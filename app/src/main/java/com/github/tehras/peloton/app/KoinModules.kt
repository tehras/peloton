package com.github.tehras.peloton.app

import com.github.tehras.peloton.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelsModule = module {
    viewModel { HomeViewModel(get()) }
}