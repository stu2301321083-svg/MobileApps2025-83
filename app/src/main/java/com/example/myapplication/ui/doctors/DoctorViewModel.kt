package com.example.myapplication.ui.doctors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.db.DatabaseProvider
import com.example.myapplication.data.entity.DoctorEntity
import com.example.myapplication.data.repository.DoctorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class DoctorViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider.getDatabase(application).doctorDao()
    private val repository = DoctorRepository(dao)

    val doctors = repository.allDoctors
        .stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    fun addDoctor(name: String, specialization: String) {
        viewModelScope.launch {
            repository.addDoctor(name, specialization)
        }
    }

    fun updateDoctor(entity: DoctorEntity) {
        viewModelScope.launch {
            repository.updateDoctor(entity)
        }
    }

    fun deleteDoctor(entity: DoctorEntity) {
        viewModelScope.launch {
            repository.deleteDoctor(entity)
        }
    }
}