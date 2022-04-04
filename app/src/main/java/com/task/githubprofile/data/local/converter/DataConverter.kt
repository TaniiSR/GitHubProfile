package com.task.githubprofile.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromList(topics: List<String?>?): String? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(topics, type)
    }

    @TypeConverter
    fun toList(topics: String?): List<String?>? {
        if (topics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(topics, type)
    }
}