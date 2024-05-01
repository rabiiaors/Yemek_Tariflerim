package com.example.easyfood.mvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.easyfood.data.db.MealsDatabase
import com.example.easyfood.data.db.Repository
import com.example.easyfood.data.pojo.MealDB
import com.example.easyfood.data.pojo.MealDetail
import com.example.easyfood.data.pojo.RandomMealResponse
import com.example.easyfood.data.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Yemek detayları ile ilgili verilerin yönetildiği ViewModel sınıfı
class DetailsMVVM(application: Application) : AndroidViewModel(application) {
    private val mutableMealDetail = MutableLiveData<List<MealDetail>>()
    private val mutableMealBottomSheet = MutableLiveData<List<MealDetail>>()
    private  var allMeals: LiveData<List<MealDB>>
    private  var repository: Repository

    init {
        val mealDao = MealsDatabase.getInstance(application).dao()
        repository = Repository(mealDao)
        allMeals = repository.mealList
    }

    // Tüm kaydedilmiş yemekleri al
    fun getAllSavedMeals() {
        viewModelScope.launch(Dispatchers.Main) {
            // İşlemler burada yapılacak
        }
    }

    // Yemeği veritabanına ekle
    fun insertMeal(meal: MealDB) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteMeal(meal)
            withContext(Dispatchers.Main) {
                // İşlemler burada yapılacak
            }
        }
    }

    // Yemeği veritabanından sil
    fun deleteMeal(meal:MealDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMeal(meal)
    }

    // Belirli bir ID'ye sahip yemeği API'den al
    fun getMealById(id: String) {
        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
                mutableMealDetail.value = response.body()!!.meals
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }

    // Belirli bir ID'ye sahip yemeğin veritabanında kaydedilip kaydedilmediğini kontrol et
    fun isMealSavedInDatabase(mealId: String): Boolean {
        var meal: MealDB? = null
        runBlocking(Dispatchers.IO) {
            meal = repository.getMealById(mealId)
        }
        return meal != null
    }

    // Belirli bir ID'ye sahip yemeği veritabanından sil
    fun deleteMealById(mealId:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMealById(mealId)
        }
    }

    // Belirli bir ID'ye sahip yemeği API'den al (alt sayfa için)
    fun getMealByIdBottomSheet(id: String) {
        RetrofitInstance.foodApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
                mutableMealBottomSheet.value = response.body()!!.meals
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }

    // Yemek detaylarını gözlemle
    fun observeMealDetail(): LiveData<List<MealDetail>> {
        return mutableMealDetail
    }

    // Alt sayfa için yemeği gözlemle
    fun observeMealBottomSheet(): LiveData<List<MealDetail>> {
        return mutableMealBottomSheet
    }

    // Kaydedilmiş yemekleri gözlemle
    fun observeSaveMeal(): LiveData<List<MealDB>> {
        return allMeals
    }

    companion object {
        private const val TAG = "DetailsMVVM"
    }
}
