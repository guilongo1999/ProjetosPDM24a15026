package pt.ipca.news_app.domain.repository

import pt.ipca.news_app.domain.model.Multimedia
import pt.ipca.news_app.domain.model.Result
import pt.ipca.news_app.util.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(
        category: String
    ): Resource<List<Result>>

    suspend fun searchForNews(
        query: String
    ): Resource<List<Result>>
}