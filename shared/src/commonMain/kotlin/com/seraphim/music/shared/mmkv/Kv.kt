package com.seraphim.music.shared.mmkv
import com.ctrip.flight.mmkv.defaultMMKV

val kv = defaultMMKV()

fun Any.safeKvSave(key: String) {
    when (this) {
        is String -> kv[key] = this
        is Int -> kv[key] = this
        is Boolean -> kv[key] = this
        is Float -> kv[key] = this
        is Long -> kv[key] = this
        else -> throw IllegalArgumentException("Unsupported type")
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T> kvGet(key: String, defaultValue: T): T {
    return when (defaultValue) {
        is String -> kv.takeString(key, defaultValue) as T
        is Int -> kv.takeInt(key, defaultValue) as T
        is Boolean -> kv.takeBoolean(key, defaultValue) as T
        is Float -> kv.takeFloat(key, defaultValue) as T
        is Long -> kv.takeLong(key, defaultValue) as T
        else -> throw IllegalArgumentException("Unsupported type")
    }
}

fun <T> safeKvGet(key: String, defaultValue: T): T {
    return kvGet(key, defaultValue)
}