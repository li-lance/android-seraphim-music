package com.seraphim.music.di

import com.seraphim.music.shared.environment.BffConfig
import com.seraphim.music.shared.environment.Environment
import com.seraphim.music.shared.mmkv.safeKvGet
import org.koin.dsl.module

val networkModule = module {
    single {
        val environment = safeKvGet("AppStage", "dev")
        when (Environment.fromString(environment)) {
            Environment.DEV -> {
                BffConfig("musicbrainz.org/ws/2")
            }

            Environment.STAGING -> {
                BffConfig("musicbrainz.org/ws/2")
            }

            Environment.PROD -> {
                BffConfig("musicbrainz.org/ws/2")
            }
        }

    }
}