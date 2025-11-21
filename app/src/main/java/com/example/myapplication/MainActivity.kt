package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.ui.doctors.DoctorsListFragment
import com.example.myapplication.ui.pets.PetsListFragment
import com.example.myapplication.ui.appointments.AppointmentsListFragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // стартовый экран
        openFragment(DoctorsListFragment())

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_doctors -> openFragment(DoctorsListFragment())
                R.id.nav_pets -> openFragment(PetsListFragment())
                R.id.nav_appointments -> openFragment(AppointmentsListFragment())
            }
            true
        }
    }

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment)
            .commit()
    }
}