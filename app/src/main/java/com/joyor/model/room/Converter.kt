package com.joyor.model.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Product

class Converter {

    @TypeConverter
    fun categoriesListToString(categories: ArrayList<Product.Category>): String {
        return Gson().toJson(categories)
    }

    @TypeConverter
    fun stringToCategoriesList(string: String): ArrayList<Product.Category> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<Product.Category>>() {}.type)
    }

    @TypeConverter
    fun specListToString(spec: ArrayList<Product.Spec>): String {
        return Gson().toJson(spec)
    }

    @TypeConverter
    fun stringToSpecList(string: String): ArrayList<Product.Spec> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<Product.Spec>>() {}.type)
    }

    @TypeConverter
    fun optionListToString(variations: ArrayList<Product.Option>): String {
        return Gson().toJson(variations)
    }

    @TypeConverter
    fun stringToOptionList(string: String): ArrayList<Product.Option> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<Product.Option>>() {}.type)
    }

    @TypeConverter
    fun stringListToString(variations: ArrayList<String>): String {
        return Gson().toJson(variations)
    }

    @TypeConverter
    fun stringToStringList(string: String): ArrayList<String> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<String>>() {}.type)
    }
}