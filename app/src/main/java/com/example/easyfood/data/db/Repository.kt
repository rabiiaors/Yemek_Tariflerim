package com.example.easyfood.data.db

import androidx.lifecycle.LiveData
import com.example.easyfood.data.pojo.MealDB

// Veritabanı işlemlerini yönetmek için kullanılan Repository sınıfı
class Repository(private val mealDao: Dao) {

    // Tüm kaydedilmiş yemekleri LiveData olarak alır
    val mealList: LiveData<List<MealDB>> = mealDao.getAllSavedMeals()

    // Favori yemeği veritabanına ekler
    suspend fun insertFavoriteMeal(meal: MealDB) {
        mealDao.insertFavorite(meal)
    }

    // Belirli bir yemeği kimliğine göre alır
    suspend fun getMealById(mealId: String): MealDB {
        return mealDao.getMealById(mealId)
    }

    // Belirli bir yemeği kimliğine göre siler
    suspend fun deleteMealById(mealId: String) {
        mealDao.deleteMealById(mealId)
    }

    // Yemeği veritabanından siler
    suspend fun deleteMeal(meal: MealDB) = mealDao.deleteMeal(meal)
}
