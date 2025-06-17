package com.seraphim.music.di


import com.seraphim.music.shared.di.Factory
import com.seraphim.music.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Factory(get()).createRoomDatabase() }
    viewModel { HomeViewModel(get()) }
}
