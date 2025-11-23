package com.example.myapplication.ui.appointments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.entity.AppointmentEntity
import com.example.myapplication.data.entity.DoctorEntity
import com.example.myapplication.data.entity.PetEntity
import com.example.myapplication.databinding.FragmentAppointmentsListBinding
import com.example.myapplication.ui.doctors.DoctorViewModel
import com.example.myapplication.ui.pets.PetViewModel
import com.example.myapplication.utils.collectWhileStarted
import java.util.Calendar

class AppointmentsListFragment : Fragment() {

    private var _binding: FragmentAppointmentsListBinding? = null
    private val binding get() = _binding!!

    private val appointmentVM: AppointmentViewModel by viewModels()
    private val doctorVM: DoctorViewModel by viewModels()
    private val petVM: PetViewModel by viewModels()

    private lateinit var adapter: AppointmentAdapter

    private var currentDoctors: List<DoctorEntity> = emptyList()
    private var currentPets: List<PetEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AppointmentAdapter(
            doctors = { currentDoctors },
            pets = { currentPets },
            onEdit = { appointment -> showEditDialog(appointment) },
            onDelete = { appointment -> appointmentVM.deleteAppointment(appointment) }
        )

        binding.recyclerAppointments.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerAppointments.adapter = adapter

        // --- LOAD DOCTORS ---
        doctorVM.doctors.collectWhileStarted(viewLifecycleOwner) {
            currentDoctors = it
        }

        // --- LOAD PETS ---
        petVM.pets.collectWhileStarted(viewLifecycleOwner) {
            currentPets = it
        }

        // --- LOAD APPOINTMENTS ---
        appointmentVM.appointments.collectWhileStarted(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // ADD NEW APPOINTMENT
        binding.btnAddAppointment.setOnClickListener {
            showAddDialog()
        }
    }

    // -------------------------------------------------------------
    // ADD APPOINTMENT
    // -------------------------------------------------------------
    private fun showAddDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add_appointment, null)

        val spinnerDoctor = view.findViewById<Spinner>(R.id.spinnerDoctor)
        val spinnerPet = view.findViewById<Spinner>(R.id.spinnerPet)
        val btnPickDate = view.findViewById<View>(R.id.btnPickDate)
        val txtDate = view.findViewById<android.widget.TextView>(R.id.txtDate)
        val edtReason = view.findViewById<EditText>(R.id.edtReason)

        var selectedDate: String? = null

        // --- SPINNER DOCTORS ---
        val doctorNames = listOf("Pick doctor") + currentDoctors.map { it.name }
        val doctorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, doctorNames)
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDoctor.adapter = doctorAdapter

        // --- SPINNER PETS ---
        val petNames = listOf("Pick pet") + currentPets.map { it.name }
        val petAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, petNames)
        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPet.adapter = petAdapter

        // --- DATE PICKER ---
        btnPickDate.setOnClickListener {
            val c = Calendar.getInstance()
            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    selectedDate = "%04d-%02d-%02d".format(year, month + 1, day)
                    txtDate.text = selectedDate
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Add Appointment")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->

                val doctorIndex = spinnerDoctor.selectedItemPosition
                val petIndex = spinnerPet.selectedItemPosition

                // Check for empty selections
                if (doctorIndex == 0 || petIndex == 0 || selectedDate == null) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Please pick doctor, pet and date.")
                        .setPositiveButton("OK", null)
                        .show()
                    return@setPositiveButton
                }

                val doctor = currentDoctors[doctorIndex - 1]
                val pet = currentPets[petIndex - 1]

                appointmentVM.addAppointment(
                    doctorId = doctor.id,
                    petId = pet.id,
                    date = selectedDate!!,
                    reason = edtReason.text.toString()
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // -------------------------------------------------------------
    // EDIT APPOINTMENT
    // -------------------------------------------------------------
    private fun showEditDialog(a: AppointmentEntity) {
        val view = layoutInflater.inflate(R.layout.dialog_add_appointment, null)

        val spinnerDoctor = view.findViewById<Spinner>(R.id.spinnerDoctor)
        val spinnerPet = view.findViewById<Spinner>(R.id.spinnerPet)
        val btnPickDate = view.findViewById<View>(R.id.btnPickDate)
        val txtDate = view.findViewById<android.widget.TextView>(R.id.txtDate)
        val edtReason = view.findViewById<EditText>(R.id.edtReason)

        var selectedDate: String? = a.date
        txtDate.text = a.date

        // Doctor names
        val doctorNames = listOf("Pick doctor") + currentDoctors.map { it.name }
        val doctorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, doctorNames)
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDoctor.adapter = doctorAdapter

        // select current doctor
        val doctorPos = currentDoctors.indexOfFirst { it.id == a.doctorId } + 1
        spinnerDoctor.setSelection(doctorPos)

        // Pet names
        val petNames = listOf("Pick pet") + currentPets.map { it.name }
        val petAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, petNames)
        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPet.adapter = petAdapter

        // select current pet
        val petPos = currentPets.indexOfFirst { it.id == a.petId } + 1
        spinnerPet.setSelection(petPos)

        edtReason.setText(a.reason ?: "")

        // DATE PICKER
        btnPickDate.setOnClickListener {
            val (y, m, d) = a.date.split("-").map { it.toInt() }
            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    selectedDate = "%04d-%02d-%02d".format(year, month + 1, day)
                    txtDate.text = selectedDate
                },
                y, m - 1, d
            )
            dialog.show()
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Appointment")
            .setView(view)
            .setPositiveButton("Update") { _, _ ->

                val doctorIndex = spinnerDoctor.selectedItemPosition
                val petIndex = spinnerPet.selectedItemPosition

                if (doctorIndex == 0 || petIndex == 0 || selectedDate == null) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Please pick doctor, pet, and date.")
                        .setPositiveButton("OK", null)
                        .show()
                    return@setPositiveButton
                }

                val doctor = currentDoctors[doctorIndex - 1]
                val pet = currentPets[petIndex - 1]

                val updated = a.copy(
                    doctorId = doctor.id,
                    petId = pet.id,
                    date = selectedDate!!,
                    reason = edtReason.text.toString()
                )

                appointmentVM.updateAppointment(updated)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}