package com.example.easyfood.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit kütüphanesini yapılandırmak ve FoodApi'ye erişim sağlamak için kullanılan Singleton nesnesi
object RetrofitInstance {

    // FoodApi arayüzünü kullanarak API isteklerini yapan Retrofit örneği
    val foodApi:FoodApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") // API'nin temel URL'si
            .addConverterFactory(GsonConverterFactory.create()) // Gson kullanarak JSON verilerini işlemek için dönüştürücü
            .build()
            .create(FoodApi::class.java) // FoodApi arayüzünü uygulayan bir hizmet oluştur
    }
}
