package com.hire_wire_application.features.profile_page

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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hire_wire_application.R
import com.hire_wire_application.databinding.FragmentEditProfileBinding
import com.hire_wire_application.models.FirebaseAuthModel
import com.hire_wire_application.models.Repository
import com.hire_wire_application.models.db_models.User
import com.squareup.picasso.Picasso

class EditProfileFragment : Fragment() {
  private lateinit var binding: FragmentEditProfileBinding
  private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
  private val firebaseAuth = FirebaseAuthModel()
  private var isImageChanged: Boolean = false
  private var originalName: String? = null
  private var originalBio: String? = null

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)

    togglePageVisibility(true)

    Repository.shared.getUserById(firebaseAuth.getLoggedInUserId()) { user ->
      if (user != null) {
        Picasso.get().load(user.imageUrl).into(binding.currProfileImage)
        binding.nameInput.setText(user.name)
        binding.bioInput.setText(user.bio)

        originalName = user.name
        originalBio = user.bio
      }

      togglePageVisibility(false)
    }

    cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
          bitmap?.let {
            binding.currProfileImage.setImageBitmap(it)
            isImageChanged = true
          } ?: Toast.makeText(context, "No Image Captured", Toast.LENGTH_LONG).show()
        }

    binding.uploadProfileImageButton.setOnClickListener { cameraLauncher.launch(null) }

    binding.saveChangesButton.setOnClickListener {
      val userId = firebaseAuth.getLoggedInUserId()
      val name = binding.nameInput.text.toString().trim()
      val bio = binding.bioInput.text.toString().trim()
      val imageBitmap = (binding.currProfileImage.drawable as BitmapDrawable).toBitmap()

      val isExistingUser = originalName != null && originalBio != null

      if (name.isEmpty() || bio.isEmpty()) {
        Toast.makeText(binding.root.context, "Empty Fields Detected", Toast.LENGTH_LONG).show()
      } else {
        togglePageVisibility(true)
        if (!isExistingUser) {
          createNewUser(userId, name, bio, imageBitmap)
        } else {
          updateExistingUser(userId, name, bio, imageBitmap)
        }
      }
    }

    return binding.root
  }

  fun createNewUser(userId: String, name: String, bio: String, imageBitmap: Bitmap) {
    val newUser = User(id = userId, name = name, bio = bio)

    Repository.shared.addUser(newUser, imageBitmap) {
      togglePageVisibility(false)
      findNavController().navigate(R.id.action_global_profilePageFragment)
    }
  }

  fun updateExistingUser(userId: String, name: String, bio: String, imageBitmap: Bitmap) {
    var updatedData: Map<String, String> = emptyMap()

    if (name != originalName) {
      updatedData = updatedData + ("name" to name)
    }

    if (bio != originalBio) {
      updatedData = updatedData + ("bio" to bio)
    }

    if (!updatedData.isEmpty() || isImageChanged) {
      Repository.shared.editUserById(
          userId,
          updatedData,
          if (isImageChanged) imageBitmap else null,
      ) {
        togglePageVisibility(false)
        findNavController().navigate(R.id.action_global_profilePageFragment)
      }
    } else {
      togglePageVisibility(false)
      Toast.makeText(binding.root.context, "No Changes Detected", Toast.LENGTH_LONG).show()
    }
  }

  fun togglePageVisibility(isLoading: Boolean) {
    binding.queryProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    val pageVisibilityState = if (isLoading) View.GONE else View.VISIBLE

    binding.currProfileImage.visibility = pageVisibilityState
    binding.uploadProfileImageButton.visibility = pageVisibilityState
    binding.nameInputText.visibility = pageVisibilityState
    binding.nameInput.visibility = pageVisibilityState
    binding.bioInputText.visibility = pageVisibilityState
    binding.bioInput.visibility = pageVisibilityState
    binding.saveChangesButton.visibility = pageVisibilityState
  }
}
