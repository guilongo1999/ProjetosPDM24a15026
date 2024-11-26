package pt.ipca.experiencia9.data.Repository

import pt.ipca.experiencia9.data.remote.NewsApi
//import pt.ipca.experiencia9.domain.model.Data
import pt.ipca.experiencia9.domain.model.Multimedia
import pt.ipca.experiencia9.domain.model.NewsResponse
import pt.ipca.experiencia9.domain.model.Result
import pt.ipca.experiencia9.domain.repository.NewsRepository
import pt.ipca.experiencia9.util.Resource


class NewsRepositoryImplementation(

    private val NewsApi:NewsApi

):NewsRepository{

    override suspend fun getTopHeadlines(category: String): Resource<List<Result>> { //Ctrl + i

        return try{
            val response = NewsApi.getBreakingNews(section = category)
            Resource.Success(response.results)
        }catch (e:Exception) {
            Resource.Error("Failed to fetch News ${e.message}")
        }

    }
}