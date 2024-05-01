package com.example.easyfood.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.pojo.Meal
import com.example.easyfood.data.pojo.MealsResponse
import com.example.easyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Yemeklerin kategoriye göre alındığı etkinlik ile ilgili verilerin yönetildiği ViewModel sınıfı
class MealActivityMVVM():ViewModel() {
    private var mutableMeal = MutableLiveData<List<Meal>>()

    // Belirli bir kategoriye ait yemekleri alma işlemi
    fun getMealsByCategory(category:String){
        RetrofitInstance.foodApi.getMealsByCategory(category).enqueue(object : Callback<MealsResponse>{
            override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
                mutableMeal.value = response.body()!!.meals
            }

            override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
                Log.d(TAG,t.message.toString())
            }

        })
    }

    // Yemekleri gözlemle
    fun observeMeal():LiveData<List<Meal>>{
        return mutableMeal
    }
}
