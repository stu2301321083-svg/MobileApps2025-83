package com.example.myapplication.ui.pets

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.data.entity.PetEntity
import com.example.myapplication.databinding.FragmentPetQrBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class PetQRFragment : Fragment() {

    private var _binding: FragmentPetQrBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_PET = "pet"

        fun newInstance(pet: PetEntity): PetQRFragment {
            val args = Bundle().apply {
                putLong("id", pet.id)
                putString("name", pet.name)
                putString("type", pet.type)
            }
            return PetQRFragment().apply { arguments = args }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getLong("id")
        val name = requireArguments().getString("name") ?: ""
        val type = requireArguments().getString("type") ?: ""

        val json = """{"id":$id,"name":"$name","type":"$type"}"""

        val bitmap = BarcodeEncoder().encodeBitmap(json, BarcodeFormat.QR_CODE, 800, 800)
        binding.imgQR.setImageBitmap(bitmap)
        binding.txtInfo.text = "$name ($type)"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}