package com.demo.listdarktheme.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.listdarktheme.R
import com.demo.listdarktheme.ui.model.RecipeModel
import de.hdodenhof.circleimageview.CircleImageView

class FoodAdapter :
    RecyclerView.Adapter<FoodAdapter.FoodAdapterViewAdapter>() {

    private val mFoodList: ArrayList<RecipeModel> = ArrayList()

    inner class FoodAdapterViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageFood: CircleImageView = itemView.findViewById(R.id.image_food)
        private val textTitle: AppCompatTextView = itemView.findViewById(R.id.text_title)
        private val textDescription: AppCompatTextView =
            itemView.findViewById(R.id.text_description)

        fun bindData(foodItem: RecipeModel) {
            textTitle.text = foodItem.name
            textDescription.text = "By: " + foodItem.credit_name
            Glide.with(itemView).load(foodItem.thumbnail_url).into(imageFood)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapterViewAdapter {
        return FoodAdapterViewAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FoodAdapterViewAdapter, position: Int) {
        if (mFoodList.size > 0)
            holder.bindData(mFoodList[position])
    }

    override fun getItemCount() = mFoodList.size

    fun updateList(foodList: ArrayList<RecipeModel>) {
        mFoodList.addAll(foodList)
        notifyDataSetChanged()
    }
}