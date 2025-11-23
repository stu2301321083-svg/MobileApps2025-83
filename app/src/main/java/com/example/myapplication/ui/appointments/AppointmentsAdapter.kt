package com.example.myapplication.ui.appointments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.entity.AppointmentEntity
import com.example.myapplication.databinding.ItemAppointmentBinding
import com.example.myapplication.data.entity.DoctorEntity
import com.example.myapplication.data.entity.PetEntity

class AppointmentAdapter(
    private val doctors: () -> List<DoctorEntity>,
    private val pets: () -> List<PetEntity>,
    private val onEdit: (AppointmentEntity) -> Unit,
    private val onDelete: (AppointmentEntity) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentVH>() {

    private var items: List<AppointmentEntity> = emptyList()

    fun submitList(list: List<AppointmentEntity>) {
        items = list
        notifyDataSetChanged()
    }

    class AppointmentVH(val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentVH {
        val binding = ItemAppointmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppointmentVH(binding)
    }

    override fun onBindViewHolder(holder: AppointmentVH, position: Int) {
        val item = items[position]

        val doctor = doctors().firstOrNull { it.id == item.doctorId }
        val pet = pets().firstOrNull { it.id == item.petId }

        holder.binding.txtDoctor.text = "Doctor: ${doctor?.name ?: "Unknown"}"
        holder.binding.txtPet.text = "Pet: ${pet?.name ?: "Unknown"}"
        holder.binding.txtDate.text = "Date: ${item.date}"
        holder.binding.txtReason.text = "Reason: ${item.reason ?: "-"}"

        holder.binding.btnEdit.setOnClickListener { onEdit(item) }
        holder.binding.btnDelete.setOnClickListener { onDelete(item) }
    }

    override fun getItemCount(): Int = items.size
}