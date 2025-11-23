package com.example.myapplication.data.repository

import com.example.myapplication.data.dao.AppointmentDao
import com.example.myapplication.data.entity.AppointmentEntity
import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val dao: AppointmentDao) {

    val allAppointments: Flow<List<AppointmentEntity>> = dao.getAllAppointments()

    suspend fun addAppointment(
        doctorId: Long,
        petId: Long,
        date: String,
        reason: String?
    ) {
        val a = AppointmentEntity(
            doctorId = doctorId,
            petId = petId,
            date = date,
            reason = reason
        )
        dao.insertAppointment(a)
    }

    suspend fun updateAppointment(a: AppointmentEntity) {
        dao.updateAppointment(a)
    }

    suspend fun deleteAppointment(a: AppointmentEntity) {
        dao.deleteAppointment(a)
    }
}