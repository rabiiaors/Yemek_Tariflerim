package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.pojo.Meal
import com.example.easyfood.databinding.MostPopularCardBinding

// En popüler yemeklerin RecyclerView içinde görüntülenmesini sağlayan adaptör sınıfı
class MostPopularRecyclerAdapter : RecyclerView.Adapter<MostPopularRecyclerAdapter.MostPopularMealViewHolder>() {
    private var mealsList: List<Meal> = ArrayList()
    private lateinit var onItemClick: OnItemClick
    private lateinit var onLongItemClick: OnLongItemClick

    // Yemek listesini günceller
    fun setMealList(mealsList: List<Meal>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    // Öğeye tıklama olayını ayarlar
    fun setOnClickListener(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    // Öğe üzerinde uzun tıklama olayını ayarlar
    fun setOnLongCLickListener(onLongItemClick: OnLongItemClick) {
        this.onLongItemClick = onLongItemClick
    }

    // Yemek görünüm tutucusu
    class MostPopularMealViewHolder(val binding: MostPopularCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularMealViewHolder {
        return MostPopularMealViewHolder(
            MostPopularCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MostPopularMealViewHolder, position: Int) {
        val i = position
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(mealsList[position].strMealThumb)
                .into(imgPopularMeal)
        }

        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(mealsList[position])
        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                onLongItemClick.onItemLongClick(mealsList[i])
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}

// Öğeye tıklama olayını belirten arayüz
interface OnItemClick {
    fun onItemClick(meal: Meal)
}

// Öğe üzerinde uzun tıklama olayını belirten arayüz
interface OnLongItemClick {
    fun onItemLongClick(meal: Meal)
}
