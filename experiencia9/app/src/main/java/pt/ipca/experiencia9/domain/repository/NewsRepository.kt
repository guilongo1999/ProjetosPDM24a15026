package pt.ipca.experiencia9.domain.repository

import pt.ipca.experiencia9.domain.model.Data
import pt.ipca.experiencia9.util.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(
        category: String
    ): Resource<List<Data>>
}