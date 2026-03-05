package com.hire_wire_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hire_wire_application.databinding.FragmentCreateServiceBinding
import com.hire_wire_application.models.Model
import com.hire_wire_application.models.Service

class CreateServiceFragment : Fragment() {
  private lateinit var binding: FragmentCreateServiceBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentCreateServiceBinding.inflate(layoutInflater, container, false)

    binding.saveButton.setOnClickListener {
      val serviceTitle = binding.titleInput.text.toString()
      val serviceDescription = binding.descriptionInput.text.toString()
      val servicePrice = binding.priceInput.text.toString().toInt()

      val newService =
          Service(
              id = java.util.UUID.randomUUID().toString(),
              imageUrl = null,
              title = serviceTitle,
              description = serviceDescription,
              price = servicePrice,
          )

      Model.shared.addService(newService) {
        binding.titleInput.text.clear()
        binding.descriptionInput.text.clear()
        binding.priceInput.text.clear()

        findNavController().navigate(R.id.action_global_exploreServicesFragment)
      }
    }

    return binding.root
  }
}
