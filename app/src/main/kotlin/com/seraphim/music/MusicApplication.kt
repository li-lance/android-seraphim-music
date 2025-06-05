package com.seraphim.music

import android.app.Application
import com.seraphim.domain.login.di.authModule
import com.seraphim.music.di.appModule
import com.seraphim.music.di.networkModule
import com.seraphim.music.kv.initMMKV
import com.seraphim.music.shared.di.sharedCommonModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MusicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initMMKV(this)
        Napier.base(DebugAntilog())
        startKoin {
            androidLogger()
            androidContext(this@MusicApplication)
            modules(networkModule + sharedCommonModule + appModule+ authModule)
        }
    }
}