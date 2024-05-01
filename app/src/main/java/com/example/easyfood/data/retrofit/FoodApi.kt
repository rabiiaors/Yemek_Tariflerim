package com.example.easyfood.data.retrofit

import com.example.easyfood.data.pojo.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit aracılığıyla yiyecek verilerine erişmek için kullanılan API arayüzü
interface FoodApi {

    // Tüm kategorileri getiren istek
    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>

    // Belirli bir kategoriye göre yemekleri getiren istek
    @GET("filter.php?")
    fun getMealsByCategory(@Query("i") category:String):Call<MealsResponse>

    // Rastgele bir yemek getiren istek
    @GET ("random.php")
    fun getRandomMeal():Call<RandomMealResponse>

    // Belirli bir yemeğin kimliğine göre yemek getiren istek
    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String):Call<RandomMealResponse>

    // Belirli bir yemeği adına göre arayan istek
    @GET("search.php?")
    fun getMealByName(@Query("s") s:String):Call<RandomMealResponse>
}