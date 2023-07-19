package com.ssafy.gumi_life_project.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.data.model.Meal
import com.ssafy.gumi_life_project.databinding.ItemMenuBinding

class MealAdapter : RecyclerView.Adapter<MealAdapter.ViewHolder>() {
    private var mealList: MutableList<Meal> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMenuBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealList[position])
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.meal = meal
            binding.executePendingBindings()
        }
    }

    fun setMealList(meal : List<Meal>) {
        mealList.clear()
        mealList.addAll(meal)
        notifyDataSetChanged()
    }
}