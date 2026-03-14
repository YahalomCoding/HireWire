package com.hire_wire_application.features.my_dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DashboardPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> MyServicesFragment()
      1 -> RequestsFragment()
      else -> throw IllegalArgumentException("Invalid position")
    }
  }
}
