package com.seraphim.domain.login.di

import com.seraphim.core.auth.TokenProvider
import com.seraphim.domain.login.AuthManager
import com.seraphim.domain.login.AuthResult
import com.seraphim.domain.login.TokenProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {
    single<TokenProvider> { TokenProviderImpl() }
    single { AuthManager(androidContext(),get()) }
    single { AuthResult(get(), get()) }
}