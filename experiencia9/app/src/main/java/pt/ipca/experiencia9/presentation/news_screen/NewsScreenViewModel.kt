package pt.ipca.experiencia9.presentation.news_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pt.ipca.experiencia9.domain.repository.NewsRepository
import pt.ipca.experiencia9.domain.model.Data
import kotlinx.coroutines.launch
import pt.ipca.experiencia9.util.Resource
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(

    private val newsRepository:NewsRepository
):ViewModel() {

    var datas by mutableStateOf<List<Data>>(emptyList())

    var state by mutableStateOf(NewsScreenState())

    fun onEvent(

        event:NewsScreenEvent
    ) {

        when(event) {
            is NewsScreenEvent.onCategoryChange -> {

                state = state.copy(category = event.category)
                getNewsArticles(state.category)
            }
            NewsScreenEvent.onCloseIconClicked -> TODO()
            is NewsScreenEvent.onNewsCardClicked -> TODO()
            NewsScreenEvent.onSearchIconClicked -> TODO()
            is NewsScreenEvent.onSearchQueryChanged -> TODO()
        }
    }

    init {

        getNewsArticles(category = "general")
    }

    private fun getNewsArticles(category:String){
        viewModelScope.launch{
            state = state.copy(isLoading = true)
            val result = newsRepository.getTopHeadlines(category = category)
            when(result) {


               is Resource.Success -> {
                  state = state.copy(datas = result.data ?: emptyList(),
                   isLoading = false

                  )
               }

                is Resource.Error -> TODO()
            }
        }
    }

}