package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.DoctorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {

    @Query("SELECT * FROM doctors ORDER BY name")
    fun getAllDoctors(): Flow<List<DoctorEntity>>

    @Insert
    suspend fun insertDoctor(doctor: DoctorEntity)

    @Update
    suspend fun updateDoctor(doctor: DoctorEntity)

    @Delete
    suspend fun deleteDoctor(doctor: DoctorEntity)
}