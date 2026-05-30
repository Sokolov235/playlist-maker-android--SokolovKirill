package com.example.myapplication.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.creator.Creator
import com.example.myapplication.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val repository: TracksRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Initial)
    val state = _state.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = SearchState.Searching

                val result = repository.searchTracks(query)

                _state.value = SearchState.Success(result)

            } catch (e: IOException) {
                _state.value = SearchState.Fail(e.message ?: "Ошибка")
            }
        }
    }

    companion object {
        fun factory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(
                        Creator.getTracksRepository()
                    ) as T
                }
            }
    }
}