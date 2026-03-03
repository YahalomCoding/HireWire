package com.hire_wire_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hire_wire_application.databinding.FragmentExploreServicesBinding

class ExploreServicesFragment : Fragment() {
  private lateinit var binding: FragmentExploreServicesBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentExploreServicesBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  //    companion object {
  //        @JvmStatic
  //        fun newInstance() = ExploreServicesFragment().apply { arguments = Bundle().apply {} }
  //    }
}
