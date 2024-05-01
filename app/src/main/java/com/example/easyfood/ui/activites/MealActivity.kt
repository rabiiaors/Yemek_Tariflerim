package com.example.easyfood.ui.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.adapters.MealRecyclerAdapter
import com.example.easyfood.adapters.SetOnMealClickListener
import com.example.easyfood.data.pojo.Meal
import com.example.easyfood.databinding.ActivityCategoriesBinding
import com.example.easyfood.mvvm.MealActivityMVVM
import com.example.easyfood.ui.fragments.HomeFragment
import com.example.easyfood.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_STR
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_THUMB

class MealActivity : AppCompatActivity() {
    private lateinit var mealActivityMvvm: MealActivityMVVM
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var myAdapter: MealRecyclerAdapter
    private var categoryNme = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Bağlayıcı oluşturma
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MVVM modelini oluşturma
        mealActivityMvvm = ViewModelProviders.of(this)[MealActivityMVVM::class.java]

        // Yükleniyor animasyonunu başlatma
        startLoading()

        // RecyclerView için hazırlıkları yapma
        prepareRecyclerView()

        // Kategoriye ait yemekleri alma
        mealActivityMvvm.getMealsByCategory(getCategory())

        // Kategoriye ait yemeklerin gözlemlenmesi
        mealActivityMvvm.observeMeal().observe(this, object : Observer<List<Meal>> {
            override fun onChanged(t: List<Meal>?) {
                if(t==null){
                    // Eğer kategoriye ait yemek yoksa, geri dön
                    hideLoading()
                    Toast.makeText(applicationContext, "No meals in this category", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }else {
                    // Yemekler varsa, RecyclerView'a göster
                    myAdapter.setCategoryList(t!!)
                    binding.tvCategoryCount.text = categoryNme + " : " + t.size.toString()
                    hideLoading()
                }
            }
        })

        // Yemek öğesine tıklama olayını ayarlama
        myAdapter.setOnMealClickListener(object : SetOnMealClickListener {
            override fun setOnClickListener(meal: Meal) {
                // Yemek detayları etkinliğine geçiş
                val intent = Intent(applicationContext, MealDetailesActivity::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_STR, meal.strMeal)
                intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                startActivity(intent)
            }
        })
    }

    // Yükleniyor animasyonunu gizleme
    private fun hideLoading() {
        binding.apply {
            loadingGifMeals.visibility = View.INVISIBLE
            mealRoot.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }

    // Yükleniyor animasyonunu başlatma
    private fun startLoading() {
        binding.apply {
            loadingGifMeals.visibility = View.VISIBLE
            mealRoot.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.g_loading))
        }
    }

    // Kategoriyi alma
    private fun getCategory(): String {
        val tempIntent = intent
        val x = intent.getStringExtra(CATEGORY_NAME)!!
        categoryNme = x
        return x
    }

    // RecyclerView için hazırlıkları yapma
    private fun prepareRecyclerView() {
        myAdapter = MealRecyclerAdapter()
        binding.mealRecyclerview.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}
