package com.seraphim.music.shared.di

import com.seraphim.music.api.PlaylistsApi
import com.seraphim.music.api.UsersApi
import com.seraphim.music.invoker.infrastructure.ApiClient
import com.seraphim.music.shared.database.AppDatabase
import com.seraphim.music.shared.database.AppDatabaseConstructor
import com.seraphim.music.shared.network.HttpClientProvider
import com.seraphim.music.shared.repository.FeaturePlayListPagingSource
import com.seraphim.music.shared.repository.UserRepository
import org.koin.dsl.module

val sharedCommonModule = module {
    single { AppDatabaseConstructor.initialize() }
    single { HttpClientProvider(get()) }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().playlistsDao() }
    single { UserRepository(get(), get()) }
    single { PlaylistsApi(ApiClient.BASE_URL, get<HttpClientProvider>().client) }
    single { UsersApi(ApiClient.BASE_URL, get<HttpClientProvider>().client) }
    single { FeaturePlayListPagingSource(get(),get()) }
}