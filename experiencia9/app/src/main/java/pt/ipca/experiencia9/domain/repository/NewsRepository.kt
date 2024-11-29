package pt.ipca.experiencia9.domain.repository

import pt.ipca.experiencia9.domain.model.Multimedia
import pt.ipca.experiencia9.domain.model.Result
import pt.ipca.experiencia9.util.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(
        category: String
    ): Resource<List<Result>>

    suspend fun searchForNews(
        query: String
    ): Resource<List<Result>>
}