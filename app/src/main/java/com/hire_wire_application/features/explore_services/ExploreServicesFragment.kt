package com.hire_wire_application.features.explore_services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hire_wire_application.features.explore_services.ServicesAdapter
import com.hire_wire_application.databinding.FragmentExploreServicesBinding
import com.hire_wire_application.models.Model

class ExploreServicesFragment : Fragment() {
  private lateinit var binding: FragmentExploreServicesBinding

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentExploreServicesBinding.inflate(layoutInflater, container, false)

    binding.servicesRecyclerView.setHasFixedSize(true)
    binding.servicesRecyclerView.layoutManager = LinearLayoutManager(context)

    binding.progressBar.visibility = View.VISIBLE

    Model.Companion.shared.getHomeFeedServices { homeFeedServices ->
      binding.servicesRecyclerView.adapter = ServicesAdapter(homeFeedServices)
      binding.progressBar.visibility = View.GONE
    }

    return binding.root
  }
}