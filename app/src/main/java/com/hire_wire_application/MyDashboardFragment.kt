package com.hire_wire_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hire_wire_application.databinding.FragmentMyDashboardBinding

class MyDashboardFragment : Fragment() {
  private lateinit var binding: FragmentMyDashboardBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentMyDashboardBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  //    companion object {
  //        @JvmStatic
  //        fun newInstance() = MyDashboardFragment().apply { arguments = Bundle().apply {} }
  //    }
}
