package com.learn.currencyconvertor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.currencyconvertor.data.remote.models.CurrencyRate
import com.learn.currencyconvertor.databinding.SingleItemBindingBinding

/**
created by Rachit on 2/22/2024.
 */
class RvAdapter(private val list: List<CurrencyRate>,
                private val onItemClicked: (CurrencyRate) -> Unit

) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SingleItemBindingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBindingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            with(list[position]) {
                holder.itemView.setOnClickListener { onItemClicked(this) }

                binding.idTVCourse.text = this.name
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}
