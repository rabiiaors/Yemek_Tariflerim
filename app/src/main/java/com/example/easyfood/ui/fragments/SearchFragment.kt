package com.example.easyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.easyfood.adapters.MealRecyclerAdapter
import com.example.easyfood.data.pojo.MealDetail
import com.example.easyfood.databinding.FragmentSearchBinding
import com.example.easyfood.mvvm.SearchMVVM
import com.example.easyfood.ui.activites.MealDetailesActivity
import com.example.easyfood.ui.fragments.HomeFragment
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_STR
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_THUMB

// Yemek arama ekranı fragment sınıfı
class SearchFragment : Fragment() {
    private lateinit var myAdapter: MealRecyclerAdapter
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchMvvm: SearchMVVM
    private var mealId = ""
    private var mealStr = ""
    private var mealThub = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewModel ve adaptörlerin oluşturulması
        myAdapter = MealRecyclerAdapter()
        searchMvvm = ViewModelProviders.of(this)[SearchMVVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bağlayıcı oluşturma
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Arama ve sonuçların gözlenmesi
        onSearchClick()
        observeSearchLiveData()
        setOnMealCardClick()
    }

    // Yemek kartına tıklama işlemi
    private fun setOnMealCardClick() {
        binding.searchedMealCard.setOnClickListener {
            // Yemek detayları sayfasına gitmek için intent oluşturulması
            val intent = Intent(context, MealDetailesActivity::class.java)
            intent.putExtra(MEAL_ID, mealId)
            intent.putExtra(MEAL_STR, mealStr)
            intent.putExtra(MEAL_THUMB, mealThub)
            startActivity(intent)
        }
    }

    // Arama düğmesine tıklama işlemi
    private fun onSearchClick() {
        binding.icSearch.setOnClickListener {
            // ViewModel aracılığıyla yemek detaylarını arama işlemi
            searchMvvm.searchMealDetail(binding.edSearch.text.toString(),context)
        }
    }

    // Arama sonuçlarının gözlenmesi
    private fun observeSearchLiveData() {
        searchMvvm.observeSearchLiveData()
            .observe(viewLifecycleOwner, object : Observer<MealDetail> {
                override fun onChanged(t: MealDetail?) {
                    if (t == null) {
                        // Aranan yemek bulunamadığında kullanıcıya bilgi verme
                        Toast.makeText(context, "No such a meal", Toast.LENGTH_SHORT).show()
                    } else {
                        // Aranan yemeğin detaylarının gösterilmesi
                        binding.apply {
                            mealId = t.idMeal
                            mealStr = t.strMeal
                            mealThub = t.strMealThumb
                            Glide.with(context!!.applicationContext)
                                .load(t.strMealThumb)
                                .into(imgSearchedMeal)
                            tvSearchedMeal.text = t.strMeal
                            searchedMealCard.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }
}
