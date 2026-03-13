package com.hire_wire_application.features.my_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hire_wire_application.databinding.FragmentMyServicesBinding

class MyServicesFragment : Fragment() {
  private lateinit var binding: FragmentMyServicesBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentMyServicesBinding.inflate(inflater, container, false)
    return binding.root
  }
}
