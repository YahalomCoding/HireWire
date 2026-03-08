package com.hire_wire_application.features.profile_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hire_wire_application.R
import com.hire_wire_application.databinding.FragmentProfilePageBinding
import com.hire_wire_application.models.FirebaseAuthModel
import com.hire_wire_application.models.Model
import com.squareup.picasso.Picasso

class ProfilePageFragment : Fragment() {
  private lateinit var binding: FragmentProfilePageBinding
  private val firebaseAuth = FirebaseAuthModel()

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentProfilePageBinding.inflate(layoutInflater, container, false)

    togglePageVisibility(true)

    Model.Companion.shared.getUserById(firebaseAuth.getLoggedInUserId()) { user ->
      if (user != null) {
        Picasso.get().load(user.imageUrl).into(binding.profileImage)
        binding.nameText.text = user.name
        binding.bioText.text = user.bio
      } else {
        findNavController().navigate(R.id.action_profilePageFragment_to_editProfileFragment)
      }

      togglePageVisibility(false)
    }

    binding.editButton.setOnClickListener {
      findNavController().navigate(R.id.action_profilePageFragment_to_editProfileFragment)
    }

    return binding.root
  }

  fun togglePageVisibility(isLoading: Boolean) {
    binding.userDataProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    val pageVisibilityState = if (isLoading) View.GONE else View.VISIBLE

    binding.profileImage.visibility = pageVisibilityState
    binding.nameText.visibility = pageVisibilityState
    binding.userProfileText.visibility = pageVisibilityState
    binding.bioText.visibility = pageVisibilityState
    binding.editButton.visibility = pageVisibilityState
  }
}