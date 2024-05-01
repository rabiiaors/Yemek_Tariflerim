package com.example.easyfood.ui.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.data.pojo.MealDB
import com.example.easyfood.data.pojo.MealDetail
import com.example.easyfood.databinding.ActivityMealDetailesBinding
import com.example.easyfood.mvvm.DetailsMVVM
import com.example.easyfood.ui.fragments.HomeFragment
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_STR
import com.example.easyfood.ui.fragments.HomeFragment.Companion.MEAL_THUMB
import com.google.android.material.snackbar.Snackbar

class MealDetailesActivity : AppCompatActivity() {

    // View Binding kullanarak ActivityMealDetailesBinding nesnesi oluşturuluyor.
    private lateinit var binding: ActivityMealDetailesBinding

    // ViewModel nesnesi oluşturuluyor.
    private lateinit var detailsMVVM: DetailsMVVM

    // Yemeğin ID'si, adı ve görseli için değişkenler.
    private var mealId = ""
    private var mealStr = ""
    private var mealThumb = ""

    // Yemeğe ait YouTube URL'si için değişken.
    private var ytUrl = ""

    // Yemek detayları MealDetail tipinde tanımlanıyor.
    private lateinit var dtMeal: MealDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding ile layout dosyası bağlanıyor.
        binding = ActivityMealDetailesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModelProviders ile ViewModel nesnesi oluşturuluyor.
        detailsMVVM = ViewModelProviders.of(this)[DetailsMVVM::class.java]

        // Yüklenme göstergesi gösteriliyor.
        showLoading()

        // Intent'ten yemek bilgileri alınıyor.
        getMealInfoFromIntent()

        // Yemek bilgileri ile görünüm ayarlanıyor.
        setUpViewWithMealInformation()

        // Kaydetme düğmesinin durumu ayarlanıyor.
        setFloatingButtonStatues()

        // ViewModel üzerinden yemek detayları alınıyor.
        detailsMVVM.getMealById(mealId)

        // Yemek detayları gözlemleniyor.
        detailsMVVM.observeMealDetail().observe(this, Observer<List<MealDetail>> { mealDetailList ->
            mealDetailList?.let {
                setTextsInViews(it[0]) // Yemek detayları görünüme ayarlanıyor.
                stopLoading() // Yükleme tamamlandı, gösterge gizleniyor.
            }
        })

        // YouTube düğmesine tıklama dinleyicisi atanıyor.
        binding.imgYoutube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl))) // YouTube uygulaması açılıyor.
        }

        // Kaydetme düğmesine tıklama dinleyicisi atanıyor.
        binding.btnSave.setOnClickListener {
            if (isMealSavedInDatabase()) { // Yemek veritabanında kayıtlıysa
                deleteMeal() // Yemeği veritabanından sil.
                binding.btnSave.setImageResource(R.drawable.ic_baseline_save_24) // Kaydetme düğmesinin ikonunu değiştir.
                // Kullanıcıya silme işlemi bildiriliyor.
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Meal was deleted",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else { // Yemek veritabanında kayıtlı değilse
                saveMeal() // Yemeği veritabanına kaydet.
                binding.btnSave.setImageResource(R.drawable.ic_saved) // Kaydetme düğmesinin ikonunu değiştir.
                // Kullanıcıya kaydetme işlemi bildiriliyor.
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Meal saved",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Yükleme göstergesi gösterme fonksiyonu.
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.GONE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    // Yükleme göstergesini gizleme fonksiyonu.
    private fun stopLoading() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

    // Yemek veritabanında kayıtlı mı kontrol eden fonksiyon.
    private fun isMealSavedInDatabase(): Boolean {
        return detailsMVVM.isMealSavedInDatabase(mealId)
    }

    // Yemeği veritabanından silen fonksiyon.
    private fun deleteMeal() {
        detailsMVVM.deleteMealById(mealId)
    }

    // Kaydetme düğmesinin durumunu ayarlayan fonksiyon.
    private fun setFloatingButtonStatues() {
        if (isMealSavedInDatabase()) { // Yemek veritabanında kayıtlıysa
            binding.btnSave.setImageResource(R.drawable.ic_saved) // Kaydedilmiş ikonunu göster.
        } else { // Kayıtlı değilse
            binding.btnSave.setImageResource(R.drawable.ic_baseline_save_24) // Kaydet ikonunu göster.
        }
    }

    // Yemeği veritabanına kaydeden fonksiyon.
    private fun saveMeal() {
        // Yemek bilgileri MealDB objesine atanıyor.
        val meal = MealDB(
            dtMeal.idMeal.toInt(),
            dtMeal.strMeal,
            dtMeal.strArea,
            dtMeal.strCategory,
            dtMeal.strInstructions,
            dtMeal.strMealThumb,
            dtMeal.strYoutube
        )
        detailsMVVM.insertMeal(meal) // Yemek veritabanına ekleniyor.
    }

    // Yemek bilgilerini görünüme ayarlayan fonksiyon.
    private fun setTextsInViews(meal: MealDetail) {
        // Yemek detayları görsel arayüze yerleştiriliyor.
        this.dtMeal = meal
        ytUrl = meal.strYoutube
        binding.apply {
            tvInstructions.text = "- Instructions : "
            tvContent.text = meal.strInstructions
            tvAreaInfo.visibility = View.VISIBLE
            tvCategoryInfo.visibility = View.VISIBLE
            tvAreaInfo.text = tvAreaInfo.text.toString() + meal.strArea
            tvCategoryInfo.text = tvCategoryInfo.text.toString() + meal.strCategory
            imgYoutube.visibility = View.VISIBLE
        }
    }

    // Görünümü yemeğin bilgileriyle ayarlayan fonksiyon.
    private fun setUpViewWithMealInformation() {
        binding.apply {
            collapsingToolbar.title = mealStr // Başlık çubuğu başlıkla ayarlanıyor.
            // Görsel yemek resmi ile ayarlanıyor.
            Glide.with(applicationContext)
                .load(mealThumb)
                .into(imgMealDetail)
        }
    }

    // Intent'ten yemek bilgilerini alacak fonksiyon.
    private fun getMealInfoFromIntent() {
        val tempIntent = intent
        this.mealId = tempIntent.getStringExtra(MEAL_ID)!!
        this.mealStr = tempIntent.getStringExtra(MEAL_STR)!!
        this.mealThumb = tempIntent.getStringExtra(MEAL_THUMB)!!
    }
}
