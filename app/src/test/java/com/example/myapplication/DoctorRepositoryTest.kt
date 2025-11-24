package com.example.myapplication

import com.example.myapplication.data.dao.DoctorDao
import com.example.myapplication.data.entity.DoctorEntity
import com.example.myapplication.data.repository.DoctorRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Test

class FakeDoctorDao : DoctorDao {

    private val list = mutableListOf<DoctorEntity>()

    override fun getAllDoctors() = flow {
        emit(list)
    }

    override suspend fun insertDoctor(doctor: DoctorEntity) {
        list.add(doctor.copy(id = (list.size + 1).toLong()))
    }

    override suspend fun updateDoctor(doctor: DoctorEntity) {
        val index = list.indexOfFirst { it.id == doctor.id }
        if (index >= 0) list[index] = doctor
    }

    override suspend fun deleteDoctor(doctor: DoctorEntity) {
        list.removeIf { it.id == doctor.id }
    }
}

class DoctorRepositoryTest {

    private val dao = FakeDoctorDao()
    private val repo = DoctorRepository(dao)

    @Test
    fun testAddDoctor() = runBlocking {
        repo.addDoctor("John", "Surgery")
        val all = repo.allDoctors.first()
        assertEquals(1, all.size)
        assertEquals("John", all[0].name)
    }
}