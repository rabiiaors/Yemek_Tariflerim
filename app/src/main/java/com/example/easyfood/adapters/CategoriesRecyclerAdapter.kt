package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.data.pojo.Category
import com.example.easyfood.databinding.CategoryCardBinding

// Kategorilerin RecyclerView içinde görüntülenmesini sağlayan adaptör sınıfı
class CategoriesRecyclerAdapter : RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoryViewHolder>() {
    private var categoryList:List<Category> = ArrayList()
    private lateinit var onItemClick: OnItemCategoryClicked
    private lateinit var onLongCategoryClick:OnLongCategoryClick

    // Kategori listesini günceller
    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    // Uzun tıklama olayını ayarlar
    fun setOnLongCategoryClick(onLongCategoryClick:OnLongCategoryClick){
        this.onLongCategoryClick = onLongCategoryClick
    }

    // Öğeye tıklandığında gerçekleşecek olayı ayarlar
    fun onItemClicked(onItemClick: OnItemCategoryClicked){
        this.onItemClick = onItemClick
    }

    // Kategori öğesinin görünüm tutucusu
    class CategoryViewHolder(val binding:CategoryCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            tvCategoryName.text = categoryList[position].strCategory

            Glide.with(holder.itemView)
                .load(categoryList[position].strCategoryThumb)
                .into(imgCategory)
        }

        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(categoryList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongCategoryClick.onCategoryLongCLick(categoryList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    // Kategori öğesine tıklanma olayını belirten arayüz
    interface OnItemCategoryClicked{
        fun onClickListener(category:Category)
    }

    // Kategori öğesine uzun tıklama olayını belirten arayüz
    interface OnLongCategoryClick{
        fun onCategoryLongCLick(category:Category)
    }
}
