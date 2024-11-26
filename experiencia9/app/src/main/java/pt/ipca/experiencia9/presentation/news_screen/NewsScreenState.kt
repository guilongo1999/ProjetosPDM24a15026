package pt.ipca.experiencia9.presentation.news_screen

//import pt.ipca.experiencia9.domain.model.Data
import pt.ipca.experiencia9.domain.model.Result

data class NewsScreenState(

    val isLoading: Boolean = false,
    val datas: List<Result> = emptyList(),
    val error: String? = null,
    val isSearchBarVisible: Boolean = false,
    val selectedArticle: Result? = null,
    val category: String = "home",
    val searchQuery: String = ""
)
