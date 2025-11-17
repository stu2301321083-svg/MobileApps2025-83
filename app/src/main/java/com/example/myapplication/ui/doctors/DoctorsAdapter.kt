package com.example.myapplication.ui.doctors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.entity.DoctorEntity
import com.example.myapplication.databinding.ItemDoctorBinding

class DoctorsAdapter :
    RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>() {

    private var items: List<DoctorEntity> = emptyList()

    fun submitList(list: List<DoctorEntity>) {
        items = list
        notifyDataSetChanged()
    }

    class DoctorViewHolder(val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val item = items[position]
        holder.binding.txtName.text = item.name
        holder.binding.txtSpec.text = item.specialization
    }

    override fun getItemCount(): Int = items.size
}