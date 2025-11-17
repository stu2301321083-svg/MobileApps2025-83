package com.example.myapplication.ui.doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentDoctorsListBinding
import com.example.myapplication.ui.doctors.DoctorViewModel
import com.example.myapplication.utils.collectWhileStarted

class DoctorsListFragment : Fragment() {

    private var _binding: FragmentDoctorsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorViewModel by viewModels()

    private lateinit var adapter: DoctorsAdapter

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

        adapter = DoctorsAdapter()
        binding.recyclerDoctors.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDoctors.adapter = adapter

        viewModel.doctors.collectWhileStarted(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnAddDoctor.setOnClickListener {
            // TODO: открытие диалога добавления доктора
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}