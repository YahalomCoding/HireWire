package com.hire_wire_application.features.my_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hire_wire_application.databinding.FragmentMyDashboardBinding

class MyDashboardFragment : Fragment() {
  private lateinit var binding: FragmentMyDashboardBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentMyDashboardBinding.inflate(layoutInflater, container, false)

    val adapter = DashboardPagerAdapter(this)
    binding.viewPager.adapter = adapter

    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
          tab.text =
              when (position) {
                0 -> "My Services"
                1 -> "Requests"
                else -> null
              }
        }
        .attach()

    return binding.root
  }
}
