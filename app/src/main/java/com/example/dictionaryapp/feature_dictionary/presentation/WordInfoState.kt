package com.example.dictionaryapp.feature_dictionary.presentation

import com.example.dictionaryapp.feature_dictionary.domain.models.WordInfo

data class WordInfoState(
    val wordInfoItem : List<WordInfo> = emptyList(),
    val isLoading : Boolean = false
)