package com.example.myapplication.ui.appointments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.DatabaseProvider
import com.example.myapplication.data.entity.AppointmentEntity
import com.example.myapplication.data.entity.DoctorEntity
import com.example.myapplication.data.entity.PetEntity
import com.example.myapplication.data.repository.AppointmentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppointmentViewModel(app: Application) : AndroidViewModel(app) {

    private val db = DatabaseProvider.getDatabase(app)

    private val appointmentDao = db.appointmentDao()
    private val doctorDao = db.doctorDao()
    private val petDao = db.petDao()

    private val repository = AppointmentRepository(appointmentDao)

    val appointments = repository.allAppointments
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val doctors = doctorDao.getAllDoctors()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList<DoctorEntity>())

    val pets = petDao.getAllPets()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList<PetEntity>())

    fun addAppointment(
        doctorId: Long,
        petId: Long,
        date: String,
        reason: String?
    ) {
        viewModelScope.launch {
            repository.addAppointment(doctorId, petId, date, reason)
        }
    }

    fun updateAppointment(a: AppointmentEntity) {
        viewModelScope.launch {
            repository.updateAppointment(a)
        }
    }

    fun deleteAppointment(a: AppointmentEntity) {
        viewModelScope.launch {
            repository.deleteAppointment(a)
        }
    }
}