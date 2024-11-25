package pt.ipca.experiencia9.presentation.news_screen

import pt.ipca.experiencia9.domain.model.Data

data class NewsScreenState(

    val isLoading: Boolean = false,
    val datas: List<Data> = emptyList(),
    val error: String? = null,
    val isSearchBarVisible: Boolean = false,
    val selectedArticle: Data? = null,
    val category: String = "general",
    val searchQuery: String = ""
)
