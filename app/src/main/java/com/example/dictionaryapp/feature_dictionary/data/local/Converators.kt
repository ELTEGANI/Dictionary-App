package com.example.dictionaryapp.feature_dictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dictionaryapp.feature_dictionary.data.util.JsonParser
import com.example.dictionaryapp.feature_dictionary.domain.models.Meaning
import com.google.gson.reflect.TypeToken
import java.io.StringReader
import kotlin.collections.ArrayList

@ProvidedTypeConverter
class Converators(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromMeaningJson(json:String):List<Meaning>{
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object:TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningJson(meanings:List<Meaning>):String{
        return jsonParser.toJson(
            meanings,
            object:TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: "[]"
    }
}