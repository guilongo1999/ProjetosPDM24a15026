package pt.ipca.news_app.data.Repository

import pt.ipca.news_app.data.remote.NewsApi
//import pt.ipca.experiencia9.domain.model.Data
import pt.ipca.news_app.domain.model.Multimedia
import pt.ipca.news_app.domain.model.NewsResponse
import pt.ipca.news_app.domain.model.Result
import pt.ipca.news_app.domain.repository.NewsRepository
import pt.ipca.news_app.util.Resource


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

    override suspend fun searchForNews(query: String): Resource<List<Result>> {

        return try{
            val response = NewsApi.searchForNews(query = query)
            Resource.Success(response.results)
        }catch (e:Exception) {
            Resource.Error("Failed to fetch News ${e.message}")
        }




    }
}