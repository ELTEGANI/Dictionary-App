package com.example.dictionaryapp.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity


@Dao
interface WordInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos:List<WordInfoEntity>)

    @Query("DELETE From wordinfoentity where word IN(:words)")
    suspend fun deleteWordInfos(words:List<String>)

    @Query("SELECT * From wordinfoentity where word LIKE '%' || :word || '%'")
    suspend fun getWordInfo(word: String):List<WordInfoEntity>


}