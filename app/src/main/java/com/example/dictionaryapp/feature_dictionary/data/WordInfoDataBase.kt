package com.example.dictionaryapp.feature_dictionary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.feature_dictionary.data.local.Converators
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity


@Database(
    entities = [WordInfoEntity::class],
    version = 1
)

@TypeConverters(Converators::class)
abstract class WordInfoDataBase : RoomDatabase() {
    abstract val wordInfoDao : WordInfoDao
}