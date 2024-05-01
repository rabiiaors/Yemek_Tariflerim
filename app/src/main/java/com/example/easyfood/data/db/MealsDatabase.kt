package com.example.easyfood.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easyfood.data.pojo.MealDB
import com.example.easyfood.data.pojo.MealDetail

// Yemek verilerini saklamak için Room veritabanı
@Database(entities = [MealDB::class], version = 6)
abstract class MealsDatabase : RoomDatabase() {

    // DAO (Data Access Object) işlemlerini gerçekleştirmek için soyut bir işlev
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MealsDatabase? = null

        // Tekil veritabanı örneği almak için eşitlenmiş bir metot
        @Synchronized
        fun getInstance(context: Context): MealsDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


