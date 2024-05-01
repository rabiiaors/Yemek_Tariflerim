package com.example.easyfood.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.easyfood.data.pojo.MealDB
import com.example.easyfood.data.pojo.MealDetail

// Veritabanı işlemleri için kullanılan DAO (Data Access Object) arayüzü
@Dao
interface Dao {

    // Favori yemeği veritabanına ekleyen işlev
    @Insert
    fun insertFavorite(meal: MealDB)

    // Favori yemeği güncelleyen işlev
    @Update
    fun updateFavorite(meal:MealDB)

    // Tüm kayıtlı yemekleri getiren işlev
    @Query("SELECT * FROM meal_information order by mealId asc")
    fun getAllSavedMeals():LiveData<List<MealDB>>

    // Belirli bir yemeği kimliğine göre getiren işlev
    @Query("SELECT * FROM meal_information WHERE mealId =:id")
    fun getMealById(id:String):MealDB

    // Belirli bir yemeği kimliğine göre silen işlev
    @Query("DELETE FROM meal_information WHERE mealId =:id")
    fun deleteMealById(id:String)

    // Yemeği veritabanından silen işlev
    @Delete
    fun deleteMeal(meal:MealDB)
}
