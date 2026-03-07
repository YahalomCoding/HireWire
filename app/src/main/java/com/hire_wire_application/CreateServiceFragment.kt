package com.hire_wire_application

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hire_wire_application.databinding.FragmentCreateServiceBinding
import com.hire_wire_application.models.FirebaseAuthModel
import com.hire_wire_application.models.Model
import com.hire_wire_application.models.Service

class CreateServiceFragment : Fragment() {
  private lateinit var binding: FragmentCreateServiceBinding
  private val firebaseAuth = FirebaseAuthModel()
  private lateinit var cameraLauncher: ActivityResultLauncher<Void?>

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentCreateServiceBinding.inflate(layoutInflater, container, false)

    cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
          bitmap?.let { binding.currServiceImage.setImageBitmap(it) }
              ?: Toast.makeText(context, "No Image Captured", Toast.LENGTH_LONG).show()
        }

    binding.uploadImageButton.setOnClickListener { cameraLauncher.launch(null) }

    binding.saveButton.setOnClickListener {
      val serviceTitle = binding.titleInput.text.toString().trim()
      val serviceDescription = binding.descriptionInput.text.toString().trim()
      val priceText = binding.priceInput.text.toString()

      if (serviceTitle.isNotEmpty() && serviceDescription.isNotEmpty() && priceText.isNotEmpty()) {
        val serviceProviderId = firebaseAuth.getLoggedInUserId()
        val servicePrice = priceText.toLong()

        val newService =
            Service(
                id = java.util.UUID.randomUUID().toString(),
                title = serviceTitle,
                description = serviceDescription,
                price = servicePrice,
                providerId = serviceProviderId,
            )

        val imageBitmap = (binding.currServiceImage.drawable as BitmapDrawable).toBitmap()

        Model.shared.addService(newService, imageBitmap) {
          findNavController().navigate(R.id.action_global_exploreServicesFragment)
        }
      } else {
        Toast.makeText(binding.root.context, "Empty Fields Detected", Toast.LENGTH_LONG).show()
      }
    }

    return binding.root
  }
}
