package com.example.easyfood.mvvm

import android.util.Log
import androidx.lifecycle.*
import com.example.easyfood.data.pojo.*
import com.example.easyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Ana fragment ile ilgili verilerin yönetildiği ViewModel sınıfı
const val TAG = "MainMVVM"
class MainFragMVVM: ViewModel() {
    private val mutableCategory = MutableLiveData<CategoryResponse>()
    private val mutableRandomMeal = MutableLiveData<RandomMealResponse>()
    private val mutableMealsByCategory = MutableLiveData<MealsResponse>()


    // ViewModel oluşturulduğunda kategorileri, rastgele bir yemeği ve belirli bir kategoriye ait yemekleri al
    init {
        getRandomMeal()
        getAllCategories()
        getMealsByCategory("beef")
    }

    // Tüm kategorileri alma işlemi
    private fun getAllCategories() {
        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                mutableCategory.value = response.body()
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    // Rastgele bir yemeği alma işlemi
    private fun getRandomMeal() {
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
                mutableRandomMeal.value = response.body()
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }

    // Belirli bir kategoriye ait yemekleri alma işlemi
    private fun getMealsByCategory(category:String) {
        RetrofitInstance.foodApi.getMealsByCategory(category).enqueue(object : Callback<MealsResponse> {
            override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
                mutableMealsByCategory.value = response.body()
            }

            override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    // Kategoriye göre yemekleri gözlemle
    fun observeMealByCategory(): LiveData<MealsResponse> {
        return mutableMealsByCategory
    }

    // Rastgele bir yemeği gözlemle
    fun observeRandomMeal(): LiveData<RandomMealResponse> {
        return mutableRandomMeal
    }

    // Kategorileri gözlemle
    fun observeCategories(): LiveData<CategoryResponse> {
        return mutableCategory
    }
}
