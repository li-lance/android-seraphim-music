package com.seraphim.music.kv
import android.content.Context
import android.util.Log
import com.ctrip.flight.mmkv.initialize


fun initMMKV(context: Context) {
    val rootDir = initialize(context)
    Log.d("MMKV", "MMKV initialized at: $rootDir")
}