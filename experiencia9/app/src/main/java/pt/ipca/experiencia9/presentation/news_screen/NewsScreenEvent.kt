package pt.ipca.experiencia9.presentation.news_screen

//import pt.ipca.experiencia9.domain.model.Data
import pt.ipca.experiencia9.domain.model.Result

sealed class NewsScreenEvent {

    data class onNewsCardClicked(val data: Result): NewsScreenEvent()
    data class onCategoryChange(val section: String): NewsScreenEvent()
    data class onSearchQueryChanged(val searchQuery: String): NewsScreenEvent()
    object onSearchIconClicked: NewsScreenEvent()
    object onCloseIconClicked: NewsScreenEvent()

}
