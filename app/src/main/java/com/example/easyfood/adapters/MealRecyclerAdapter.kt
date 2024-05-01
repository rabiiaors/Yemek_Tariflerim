package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.pojo.Meal
import com.example.easyfood.databinding.MealCardBinding

// Yiyeceklerin RecyclerView içinde görüntülenmesini sağlayan adaptör sınıfı
class MealRecyclerAdapter : RecyclerView.Adapter<MealRecyclerAdapter.MealViewHolder>() {

    private var mealList: List<Meal> = ArrayList()
    private lateinit var setOnMealClickListener: SetOnMealClickListener

    // Yiyecek listesini günceller
    fun setCategoryList(mealList: List<Meal>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    // Yiyeceğe tıklama olayını ayarlar
    fun setOnMealClickListener(setOnMealClickListener: SetOnMealClickListener) {
        this.setOnMealClickListener = setOnMealClickListener
    }

    // Yiyecek görünüm tutucusu
    class MealViewHolder(val binding: MealCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(MealCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.binding.apply {
            tvMealName.text = mealList[position].strMeal
            Glide.with(holder.itemView)
                .load(mealList[position].strMealThumb)
                .into(imgMeal)
        }

        holder.itemView.setOnClickListener {
            setOnMealClickListener.setOnClickListener(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}

// Yiyeceğe tıklama olayını belirten arayüz
interface SetOnMealClickListener {
    fun setOnClickListener(meal: Meal)
}

