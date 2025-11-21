package com.example.myapplication.ui.pets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.DatabaseProvider
import com.example.myapplication.data.entity.PetEntity
import com.example.myapplication.data.repository.PetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PetViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider.getDatabase(application).petDao()
    private val repository = PetRepository(dao)

    val pets = repository.allPets
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addPet(name: String, type: String) {
        viewModelScope.launch {
            repository.addPet(name, type)
        }
    }

    fun updatePet(entity: PetEntity) {
        viewModelScope.launch {
            repository.updatePet(entity)
        }
    }

    fun deletePet(entity: PetEntity) {
        viewModelScope.launch {
            repository.deletePet(entity)
        }
    }
}