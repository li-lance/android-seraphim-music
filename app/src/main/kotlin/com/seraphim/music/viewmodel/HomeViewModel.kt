package com.seraphim.music.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.seraphim.music.shared.repository.FeaturePlayListPagingSource

class HomeViewModel(private val pagingSource:FeaturePlayListPagingSource) : ViewModel() {
    val featurePlayListPagingFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)

}