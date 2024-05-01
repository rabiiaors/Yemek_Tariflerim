package com.example.easyfood.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.ui.activites.MealDetailesActivity
import com.example.easyfood.ui.fragments.HomeFragment
import com.example.easyfood.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_AREA
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_NAME
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_STR
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_THUMB
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// Alt sayfa için yemek bilgisi görüntülemek için oluşturulan BottomSheetDialogFragment sınıfı
class MealBottomDialog() : BottomSheetDialogFragment() {
    // Yemek adı, id, görseli, ülkesi ve kategorisi için değişkenler
    private var mealName = ""
    private var mealId =""
    private var mealImg = ""
    private var mealCountry = ""
    private var mealCategory = ""

    // Oluşturucu metod
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Tema tanımlaması yapılıyor
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme)
        val b = arguments
        // Gelen veriler alınıyor
        mealName = b!!.getString(MEAL_NAME).toString()
        mealId =b!!.getString(MEAL_ID).toString()
        mealImg =b!!.getString(MEAL_THUMB).toString()
        mealCategory =b!!.getString(CATEGORY_NAME).toString()
        mealCountry =b!!.getString(MEAL_AREA).toString()
    }

    // Görünüm oluşturulduğunda çağrılan metot
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, container, false)
        return v
    }

    // Görünüm oluşturulduğunda çağrılan metot
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Görünüm hazırlanıyor
        prepareView(view)

        // Tıklama olayı ayarlanıyor
        view.setOnClickListener {
            // Yemek detaylarını gösteren aktiviteye geçiş yapılıyor
            val intent = Intent(context, MealDetailesActivity::class.java)
            intent.putExtra(MEAL_ID,mealId)
            intent.putExtra(MEAL_STR,mealName)
            intent.putExtra(MEAL_THUMB,mealImg)
            startActivity(intent)
        }

    }

    // Görünüm hazırlama metodu
    fun prepareView(view:View){
        // Görüntüleme bileşenleri tanımlanıyor
        val tvMealName = view.findViewById<TextView>(R.id.tv_meal_name_in_btmsheet)
        val tvMealCategory = view.findViewById<TextView>(R.id.tv_meal_category)
        val tvMealCountry = view.findViewById<TextView>(R.id.tv_meal_country)
        val imgMeal = view.findViewById<ImageView>(R.id.img_category)

        // Yemek görseli Glide kütüphanesi ile yükleniyor
        Glide.with(view)
            .load(mealImg)
            .into(imgMeal)
        // Diğer bilgiler set ediliyor
        tvMealName.text = mealName
        tvMealCategory.text = mealCategory
        tvMealCountry.text = mealCountry
    }
}
