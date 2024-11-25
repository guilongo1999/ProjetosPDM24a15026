package pt.ipca.experiencia9.presentation.news_screen

import pt.ipca.experiencia9.domain.model.Data

sealed class NewsScreenEvent {

    data class onNewsCardClicked(val data: Data): NewsScreenEvent()
    data class onCategoryChange(val category: String): NewsScreenEvent()
    data class onSearchQueryChanged(val searchQuery: Data): NewsScreenEvent()
    object onSearchIconClicked: NewsScreenEvent()
    object onCloseIconClicked: NewsScreenEvent()

}
