package com.example.dictionaryapp.feature_dictionary.domain.models


data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)
