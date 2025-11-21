package com.example.myapplication.data.repository

import com.example.myapplication.data.dao.PetDao
import com.example.myapplication.data.entity.PetEntity
import kotlinx.coroutines.flow.Flow

class PetRepository(private val petDao: PetDao) {

    val allPets: Flow<List<PetEntity>> = petDao.getAllPets()

    suspend fun addPet(name: String, type: String ){
        val pet = PetEntity(name = name, type = type)
        petDao.insertPet(pet)
    }
    suspend fun updatePet(pet: PetEntity){
        petDao.updatePet(pet)
    }

    suspend fun deletePet(pet: PetEntity){
        petDao.deletePet(pet)
    }
}