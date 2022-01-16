package com.example.dictionaryapp.feature_dictionary.data.remote.dto

import com.example.dictionaryapp.feature_dictionary.domain.models.WordInfo

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String,
    val phonetic: String,
    val word: String
){
   fun toWordInfo(): WordInfo {
       return WordInfo(
           meanings = meanings.map { it.toMeaning() },
           origin = origin,
           phonetic = phonetic,
           word = word
       )
   }
}