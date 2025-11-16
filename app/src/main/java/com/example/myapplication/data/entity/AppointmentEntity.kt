package com.example.myapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val doctorId: Long,
    val petId: Long,
    val date: String,   // format "YYYY-MM-DD"
    val reason: String?
)