package com.example.myapplication.data.repository

import com.example.myapplication.data.dao.DoctorDao
import com.example.myapplication.data.entity.DoctorEntity
import kotlinx.coroutines.flow.Flow

class DoctorRepository(private val doctorDao: DoctorDao) {

    val allDoctors: Flow<List<DoctorEntity>> = doctorDao.getAllDoctors()

    suspend fun addDoctor(name: String, specialization: String) {
        val doctor = DoctorEntity(name = name, specialization = specialization)
        doctorDao.insertDoctor(doctor)
    }

    suspend fun updateDoctor(doctor: DoctorEntity) {
        doctorDao.updateDoctor(doctor)
    }

    suspend fun deleteDoctor(doctor: DoctorEntity) {
        doctorDao.deleteDoctor(doctor)
    }
}