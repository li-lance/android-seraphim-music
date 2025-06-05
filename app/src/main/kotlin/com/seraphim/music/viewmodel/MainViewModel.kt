package com.seraphim.music.viewmodel

import androidx.lifecycle.ViewModel
import com.seraphim.music.shared.repository.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun user() {
        userRepository.user()
    }
}