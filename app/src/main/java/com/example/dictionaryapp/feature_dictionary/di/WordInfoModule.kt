package com.example.dictionaryapp.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.example.dictionaryapp.feature_dictionary.data.WordInfoDataBase
import com.example.dictionaryapp.feature_dictionary.data.local.Converators
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi.Companion.BASE_URL
import com.example.dictionaryapp.feature_dictionary.data.repository.WordInfoRepositoryIml
import com.example.dictionaryapp.feature_dictionary.data.util.GsonParser
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(wordInfoRepository: WordInfoRepository):GetWordInfo{
        return GetWordInfo(wordInfoRepository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        dataBase: WordInfoDataBase, api : DictionaryApi
    ) : WordInfoRepository{
        return WordInfoRepositoryIml(api,dataBase.wordInfoDao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDataBase(application: Application):WordInfoDataBase{
        return Room.databaseBuilder(application,WordInfoDataBase::class.java,"word_db")
            .addTypeConverter(Converators(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi():DictionaryApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}