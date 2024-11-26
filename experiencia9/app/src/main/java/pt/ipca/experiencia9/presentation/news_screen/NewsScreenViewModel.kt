package pt.ipca.experiencia9.presentation.news_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pt.ipca.experiencia9.domain.repository.NewsRepository
//import pt.ipca.experiencia9.domain.model.Data
import kotlinx.coroutines.launch
import pt.ipca.experiencia9.util.Resource
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.ipca.experiencia9.domain.model.Result
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(NewsScreenState())

    init {
        getNewsArticles(category = "home")
    }

    fun onEvent(event: NewsScreenEvent) {
        when (event) {
            is NewsScreenEvent.onCategoryChange -> {
                state = state.copy(category = event.section)
                getNewsArticles(state.category)
            }
            NewsScreenEvent.onCloseIconClicked -> {
                // TODO: Implement onCloseIconClicked functionality
            }
            is NewsScreenEvent.onNewsCardClicked -> {
                // TODO: Handle card click event
            }
            NewsScreenEvent.onSearchIconClicked -> {
                // TODO: Handle search icon click event
            }
            is NewsScreenEvent.onSearchQueryChanged -> {
                // TODO: Handle search query change
            }
        }
    }

    private fun getNewsArticles(category: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            val result = newsRepository.getTopHeadlines(category = category)
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        datas = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message,
                        isLoading = false,
                        datas = emptyList()
                    )
                }
            }
        }
    }
}

/*
is Resource.Success -> {
    state = state.copy(datas = result.data ?: emptyList(),
        isLoading = false,
        error = null

    )
}

is Resource.Error -> {
    state.copy(error = result.message,
        isLoading = false,
        datas = emptyList())

}

*/
