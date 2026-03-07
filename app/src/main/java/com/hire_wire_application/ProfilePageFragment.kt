package com.hire_wire_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hire_wire_application.databinding.FragmentProfilePageBinding

class ProfilePageFragment : Fragment() {
  private lateinit var binding: FragmentProfilePageBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentProfilePageBinding.inflate(layoutInflater, container, false)

    return binding.root
  }
}
