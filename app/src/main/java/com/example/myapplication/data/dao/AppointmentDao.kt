package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.AppointmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Query("SELECT * FROM appointments ORDER BY date")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Query(
        "SELECT * FROM appointments " +
                "WHERE (:doctorId IS NULL OR doctorId = :doctorId) " +
                "AND (:petId IS NULL OR petId = :petId) " +
                "AND (:date IS NULL OR date = :date)"
    )
    fun searchAppointments(
        doctorId: Long?,
        petId: Long?,
        date: String?
    ): Flow<List<AppointmentEntity>>

    @Insert
    suspend fun insertAppointment(a: AppointmentEntity)

    @Update
    suspend fun updateAppointment(a: AppointmentEntity)

    @Delete
    suspend fun deleteAppointment(a: AppointmentEntity)
}