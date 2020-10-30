package com.github.tehras.peloton.app

import com.github.tehras.peloton.home.HomeViewModel
import com.github.tehras.peloton.init.InitViewModel
import com.github.tehras.peloton.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelsModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { InitViewModel(get()) }
}