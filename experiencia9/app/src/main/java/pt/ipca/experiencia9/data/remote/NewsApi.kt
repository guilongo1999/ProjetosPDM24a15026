package pt.ipca.experiencia9.data.remote

import pt.ipca.experiencia9.domain.model.NewsResponse
import retrofit2.http.Query
import retrofit2.http.GET

interface NewsApi {

    //  https://api.thenewsapi.com/v1/news/top?api_token=IpiGxaPcql0j94Eusn2Tv36c8qp9dbdJWIv4c4u0&locale=us&limit=3

    @GET("top")
    suspend fun getBreakingNews(

        @Query("category") category:String,
        //@Query("language") language:String = "en",
        @Query("api_token") api_token:String = API_KEY,
        @Query("locale") locale:String = "us",
        @Query("limit") limit:Int = 10
    ):NewsResponse


    companion object {



        const val BASE_URL = "https://api.thenewsapi.com/v1/news/"
        const val API_KEY = "IpiGxaPcql0j94Eusn2Tv36c8qp9dbdJWIv4c4u0"
    }
}