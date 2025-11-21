package com.example.myapplication.ui.pets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.entity.PetEntity
import com.example.myapplication.databinding.ItemPetBinding

class PetsAdapter(
    private val onEdit: (PetEntity) -> Unit,
    private val onDelete: (PetEntity) -> Unit
) : RecyclerView.Adapter<PetsAdapter.PetViewHolder>() {

    private var items: List<PetEntity> = emptyList()

    fun submitList(list: List<PetEntity>) {
        items = list
        notifyDataSetChanged()
    }

    class PetViewHolder(val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val item = items[position]

        holder.binding.txtName.text = item.name
        holder.binding.txtType.text = item.type

        holder.binding.btnEdit.setOnClickListener {
            onEdit(item)
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(item)
        }
    }

    override fun getItemCount(): Int = items.size
}