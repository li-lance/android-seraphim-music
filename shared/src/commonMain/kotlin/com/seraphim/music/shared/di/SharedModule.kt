package com.seraphim.music.shared.di

import com.seraphim.music.shared.network.ApiService
import com.seraphim.music.shared.network.HttpClientProvider
import com.seraphim.music.shared.repository.UserRepository
import org.koin.dsl.module

val sharedCommonModule = module {
    single { HttpClientProvider(get()) }
    single { ApiService(get()) }
    single { UserRepository(get()) }
}