package com.hire_wire_application.features.my_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hire_wire_application.databinding.FragmentRequestsBinding

class RequestsFragment : Fragment() {
  private lateinit var binding: FragmentRequestsBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentRequestsBinding.inflate(inflater, container, false)
    return binding.root
  }
}
