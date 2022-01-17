package com.example.dictionaryapp.feature_dictionary.data.repository

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.domain.models.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryIml(
    private val dictionaryApi: DictionaryApi,
    private val wordInfoDao: WordInfoDao
) : WordInfoRepository{
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow{

        emit(Resource.Loading())
        val wordsInfo = wordInfoDao.getWordInfo(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordsInfo))

        try {
         val remoteWordInfo = dictionaryApi.getWordInfo(word)
         wordInfoDao.deleteWordInfos(remoteWordInfo.map { it.word })
         wordInfoDao.insertWordInfos(remoteWordInfo.map { it.toWordInfoEntity() })
        }catch (httpException:HttpException){
          emit(Resource.Error(
              message = "Oops.. something went Wrong",
              data = wordsInfo
          ))
        }catch (ioException:IOException){
            emit(Resource.Error(
                message = "Couldn't Reach Server..Please Check Your Internet Connection",
                data = wordsInfo
            ))
        }

        val wordInfos = wordInfoDao.getWordInfo(word).map { it.toWordInfo() }
        emit(Resource.Success(wordInfos))


    }

}