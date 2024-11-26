package pt.ipca.experiencia9.data.remote

import pt.ipca.experiencia9.domain.model.NewsResponse
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {



    @GET("{section}.json")
    suspend fun getBreakingNews(

        @Path("section") section: String,
        //@Query("category") category:String,
        @Query("api-key") apikey:String = API_KEY,

    ):NewsResponse


    companion object {



        const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
        const val API_KEY = "44JYvmqkACW6e7HmgQxMdGxVGgllmH5U"


    }
}


//  https://api.thenewsapi.com/v1/news/top?api_token=IpiGxaPcql0j94Eusn2Tv36c8qp9dbdJWIv4c4u0&locale=us&limit=3
//https://api.nytimes.com/svc/topstories/v2/{section}.json?api-key=yourkey
//@Query("language") language:String = "en",
//@Query("locale") locale:String = "us",
//@Query("limit") limit:Int = 10