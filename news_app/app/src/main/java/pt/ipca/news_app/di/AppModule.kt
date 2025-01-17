package pt.ipca.news_app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.ipca.news_app.data.Repository.NewsRepositoryImplementation
import pt.ipca.news_app.data.remote.NewsApi
import pt.ipca.news_app.data.remote.NewsApi.Companion.BASE_URL
import pt.ipca.news_app.domain.repository.NewsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi():NewsApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository {

        return NewsRepositoryImplementation(newsApi)
    }
}