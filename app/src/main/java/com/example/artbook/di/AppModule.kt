package com.example.artbook.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.artbook.R
import com.example.artbook.adapter.ArtRvAdapter
import com.example.artbook.adapter.ImageRvAdapter
import com.example.artbook.api.RetrofitAPI
import com.example.artbook.repo.ArtRepository
import com.example.artbook.repo.ArtRepositoryInterface
import com.example.artbook.roomdb.ArtDao
import com.example.artbook.roomdb.ArtDatabase
import com.example.artbook.util.Util.BASE_URL
import com.example.artbook.view.ArtFragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectDatabase(@ApplicationContext context: Context ) = Room
        .databaseBuilder(context,ArtDatabase::class.java,"ArtBookDatabase").build()


    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Provides
    @Singleton
    fun injectRetrofit():RetrofitAPI {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context ) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

    @Provides
    @Singleton
    fun injectNormalRepo(retrofitAPI: RetrofitAPI,dao: ArtDao) = ArtRepository(retrofitAPI,dao) as ArtRepositoryInterface







}