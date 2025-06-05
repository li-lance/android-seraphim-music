package com.seraphim.music.shared.environment

enum class Environment {
    DEV,
    STAGING,
    PROD;

    companion object {
        fun fromString(value: String): Environment {
            return when (value.lowercase()) {
                "dev" -> DEV
                "staging" -> STAGING
                "prod" -> PROD
                else -> throw IllegalArgumentException("Unknown environment: $value")
            }
        }
    }
}