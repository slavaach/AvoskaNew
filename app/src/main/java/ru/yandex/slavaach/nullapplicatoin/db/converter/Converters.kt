package ru.yandex.slavaach.nullapplicatoin.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class Converters {

    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return if (value != null) {
            Gson().fromJson(value, listType)
        } else {
            emptyList()
        }
    }

    @TypeConverter
    fun fromArrayListToString(list: ArrayList<String>?): String {
        val gson = Gson()
        return gson.toJson(list ?: arrayListOf<String>())
    }

    @TypeConverter
    fun fromStringArrayList(value: String?): ArrayList<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return if (value != null) {
            Gson().fromJson(value, listType)
        } else {
            arrayListOf()
        }
    }

    @TypeConverter
    fun fromArrayListToString(list: List<String>?): String {
        val gson = Gson()
        return gson.toJson(list ?: emptyList<String>())
    }

    @TypeConverter
    fun dateToLong(date: LocalDateTime?): Long {
        return date?.toEpochSecond(ZoneOffset.UTC) ?: -1
    }

    @TypeConverter
    fun longToDate(long: Long): LocalDateTime? {
        return if (long == -1L) {
            null
        } else {
            LocalDateTime.ofEpochSecond(long, 0, ZoneOffset.UTC)
        }
    }
}
