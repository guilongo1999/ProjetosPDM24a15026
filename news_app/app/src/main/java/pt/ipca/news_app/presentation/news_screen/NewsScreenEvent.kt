package pt.ipca.news_app.presentation.news_screen

//import pt.ipca.news_app.domain.model.Data
import pt.ipca.news_app.domain.model.Result

sealed class NewsScreenEvent {

    data class onNewsCardClicked(val data: Result): NewsScreenEvent()
    data class onCategoryChange(val section: String): NewsScreenEvent()
    data class onSearchQueryChanged(val searchQuery: String): NewsScreenEvent()
    object onSearchIconClicked: NewsScreenEvent()
    object onCloseIconClicked: NewsScreenEvent()

}
