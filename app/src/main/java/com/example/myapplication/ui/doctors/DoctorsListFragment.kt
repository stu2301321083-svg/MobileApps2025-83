package com.example.myapplication.ui.doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentDoctorsListBinding
//import com.example.myapplication.ui.doctors.DoctorViewModel
import com.example.myapplication.utils.collectWhileStarted
import android.app.AlertDialog
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.data.entity.DoctorEntity
//import com.example.myapplication.ui.doctors.DoctorsAdapter

class DoctorsListFragment : Fragment() {

    private var _binding: FragmentDoctorsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorViewModel by viewModels()

    private lateinit var adapter: DoctorsAdapter

    private fun showEditDialog(doctor: DoctorEntity) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_doctor, null)

        val edtName = dialogView.findViewById<EditText>(R.id.edtName)
        val edtSpec = dialogView.findViewById<EditText>(R.id.edtSpec)

        // заполняем текущими данными
        edtName.setText(doctor.name)
        edtSpec.setText(doctor.specialization)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Doctor")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newName = edtName.text.toString()
                val newSpec = edtSpec.text.toString()

                if (newName.isNotBlank() && newSpec.isNotBlank()) {
                    val updated = doctor.copy(
                        name = newName,
                        specialization = newSpec
                    )

                    viewModel.updateDoctor(updated)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DoctorsAdapter(
            onEdit = { doctor ->
                showEditDialog(doctor)
            },
            onDelete = { doctor ->
                viewModel.deleteDoctor(doctor)
            }
        )
        binding.recyclerDoctors.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDoctors.adapter = adapter

        viewModel.doctors.collectWhileStarted(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnAddDoctor.setOnClickListener {
            val dialogView = layoutInflater.inflate(
                R.layout.dialog_add_doctor,
                null
            )

            val edtName = dialogView.findViewById<EditText>(R.id.edtName)
            val edtSpec = dialogView.findViewById<EditText>(R.id.edtSpec)

            AlertDialog.Builder(requireContext())
                .setTitle("Add Doctor")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    val name = edtName.text.toString()
                    val spec = edtSpec.text.toString()

                    if (name.isNotBlank() && spec.isNotBlank()) {
                        viewModel.addDoctor(name, spec)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}