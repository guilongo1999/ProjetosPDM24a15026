package pt.ipca.news_app.presentation.news_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pt.ipca.news_app.domain.repository.NewsRepository
//import pt.ipca.experiencia9.domain.model.Data
import kotlinx.coroutines.launch
import pt.ipca.news_app.util.Resource
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import pt.ipca.news_app.domain.model.Result
import javax.inject.Inject
import kotlinx.coroutines.Job

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(NewsScreenState())

    private var searchJob: Job? = null

  //  init {
   //     getNewsArticles(category = "home")
   // }

    fun onEvent(event: NewsScreenEvent) {
        when (event) {
            is NewsScreenEvent.onCategoryChange -> {
                state = state.copy(category = event.section)
                getNewsArticles(state.category)
            }


            NewsScreenEvent.onCloseIconClicked -> {
                state = state.copy(isSearchBarVisible = false)
                getNewsArticles(category = state.category)
            }
            is NewsScreenEvent.onNewsCardClicked -> {
                state = state.copy(selectedArticle = event.data)
            }
            NewsScreenEvent.onSearchIconClicked -> {
                state = state.copy(isSearchBarVisible = true, datas = emptyList())
            }
            is NewsScreenEvent.onSearchQueryChanged -> {
               state = state.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {

                    delay(1000)
                    searchForNews(query = state.searchQuery)
                }
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

    private fun searchForNews(query: String) {
        if(query.isEmpty()) {

            return
        }
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            val result = newsRepository.searchForNews(query = query)
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
