package pt.ipca.experiencia9.data.Repository

import pt.ipca.experiencia9.data.remote.NewsApi
import pt.ipca.experiencia9.domain.model.Data
import pt.ipca.experiencia9.domain.repository.NewsRepository
import pt.ipca.experiencia9.util.Resource


class NewsRepositoryImplementation(

    private val NewsApi:NewsApi

):NewsRepository{

    override suspend fun getTopHeadlines(category: String): Resource<List<Data>> { //Ctrl + i

        return try{
            val response = NewsApi.getBreakingNews(category = category)
            Resource.Success(response.data)
        }catch (e:Exception) {
            Resource.Error("Failed to fetch News ${e.message}")
        }

    }
}