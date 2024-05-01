package com.example.easyfood.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.pojo.CategoryResponse
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Kategori verilerini yöneten ViewModel sınıfı
class CategoryMVVM : ViewModel() {
    private var categories: MutableLiveData<List<Category>> = MutableLiveData<List<Category>>()

    // ViewModel oluşturulduğunda kategorileri al
    init {
        getCategories()
    }

    // Kategorileri API'den alma işlemini gerçekleştirir
    private fun getCategories(){
        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryResponse>{
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                categories.value = response.body()!!.categories
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d(TAG,t.message.toString())
            }

        })
    }

    // Kategorileri gözlemlemek için bir LiveData döndürür
    fun observeCategories(): LiveData<List<Category>>{
        return categories
    }

    companion object {
        private const val TAG = "CategoryMVVM"
    }
}
