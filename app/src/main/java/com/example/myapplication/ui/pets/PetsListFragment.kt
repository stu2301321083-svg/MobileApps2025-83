package com.example.myapplication.ui.pets

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.entity.PetEntity
import com.example.myapplication.databinding.FragmentPetsListBinding
import com.example.myapplication.utils.collectWhileStarted

class PetsListFragment : Fragment() {

    private var _binding: FragmentPetsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PetViewModel by viewModels()

    private lateinit var adapter: PetsAdapter

    private fun showEditDialog(pet: PetEntity) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_pet, null)

        val edtName = dialogView.findViewById<EditText>(R.id.edtName)
        val edtType = dialogView.findViewById<EditText>(R.id.edtType)

        edtName.setText(pet.name)
        edtType.setText(pet.type)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Pet")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newName = edtName.text.toString()
                val newType = edtType.text.toString()

                if (newName.isNotBlank() && newType.isNotBlank()) {
                    val updated = pet.copy(
                        name = newName,
                        type = newType
                    )
                    viewModel.updatePet(updated)
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
        _binding = FragmentPetsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PetsAdapter(
            onEdit = { pet -> showEditDialog(pet) },
            onDelete = { pet -> viewModel.deletePet(pet) }
        )

        binding.recyclerPets.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPets.adapter = adapter

        viewModel.pets.collectWhileStarted(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnAddPet.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_pet, null)

            val edtName = dialogView.findViewById<EditText>(R.id.edtName)
            val edtType = dialogView.findViewById<EditText>(R.id.edtType)
            AlertDialog.Builder(requireContext())
                .setTitle("Add Pet")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    val name = edtName.text.toString()
                    val type = edtType.text.toString()

                    if (name.isNotBlank() && type.isNotBlank()) {
                        viewModel.addPet(name, type)
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