package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Query("SELECT * FROM pets ORDER BY name")
    fun getAllPets(): Flow<List<PetEntity>>

    @Insert
    suspend fun insertPet(pet: PetEntity)

    @Update
    suspend fun updatePet(pet: PetEntity)

    @Delete
    suspend fun deletePet(pet: PetEntity)
}