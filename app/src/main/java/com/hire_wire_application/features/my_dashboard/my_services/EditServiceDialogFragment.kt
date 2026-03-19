package com.hire_wire_application.features.my_dashboard.my_services

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import com.hire_wire_application.databinding.DialogEditServiceBinding
import com.hire_wire_application.models.Repository
import com.hire_wire_application.models.db_models.Service
import com.squareup.picasso.Picasso

class EditServiceDialogFragment(private val service: Service) : DialogFragment() {

  // for trash collecting, so that the binding which holds everything will not stay
  // after the child fragment is destroyed
  private var _binding: DialogEditServiceBinding? = null
  private val binding
    get() = _binding!!

  private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
  private var isImageChanged = false

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    _binding = DialogEditServiceBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupUI()
    setupListeners()
  }

  private fun setupUI() {
    binding.editTitleInput.setText(service.title)
    binding.editDescriptionInput.setText(service.description)
    binding.editPriceInput.setText(service.price.toString())

    if (service.imageUrl.isNotEmpty() && service.imageUrl != "Initial Value") {
      Picasso.get().load(service.imageUrl).into(binding.editServiceImage)
    }
  }

  private fun setupListeners() {
    cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
          bitmap?.let {
            binding.editServiceImage.setImageBitmap(it)
            isImageChanged = true
          } ?: Toast.makeText(context, "No Image Captured", Toast.LENGTH_SHORT).show()
        }

    binding.imageCard.setOnClickListener { cameraLauncher.launch(null) }

    binding.saveChangesButton.setOnClickListener { saveChanges() }
  }

  private fun saveChanges() {
    val title = binding.editTitleInput.text.toString().trim()
    val description = binding.editDescriptionInput.text.toString().trim()
    val priceText = binding.editPriceInput.text.toString().trim()

    if (title.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
      Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
      return
    }

    val price = priceText.toLongOrNull() ?: 0L

    if (
        title == service.title &&
            description == service.description &&
            price == service.price &&
            !isImageChanged
    ) {
      Toast.makeText(context, "No changes detected", Toast.LENGTH_SHORT).show()
      return
    }

    val updatedService = service.copy(title = title, description = description, price = price)

    val imageBitmap: Bitmap? =
        if (isImageChanged) {
          (binding.editServiceImage.drawable as? BitmapDrawable)?.toBitmap()
        } else {
          null
        }

    binding.editServiceProgressBar.visibility = View.VISIBLE
    binding.saveChangesButton.isEnabled = false

    Repository.shared.editService(updatedService, imageBitmap) {
      binding.editServiceProgressBar.visibility = View.GONE
      dismiss()
    }
  }

  override fun onStart() {
    super.onStart()
    dialog
        ?.window
        ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    const val TAG = "EditServiceDialogFragment"
  }
}
