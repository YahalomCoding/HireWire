package com.hire_wire_application.features.explore_services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hire_wire_application.databinding.FragmentExploreServicesBinding
import com.hire_wire_application.models.LoadingState

class ExploreServicesFragment : Fragment() {
  private lateinit var binding: FragmentExploreServicesBinding
  private val viewModel: ExploreServicesViewModel by viewModels()
  private var adapter: ServicesAdapter? = null

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentExploreServicesBinding.inflate(layoutInflater, container, false)

    binding.servicesRecyclerView.setHasFixedSize(true)
    binding.servicesRecyclerView.layoutManager = LinearLayoutManager(context)

    adapter = ServicesAdapter(emptyList())
    binding.servicesRecyclerView.adapter = adapter

    viewModel.data.observe(viewLifecycleOwner) { services ->
      adapter?.updateServices(services)
      binding.progressBar.visibility = View.GONE
      binding.swipeRefresh.isRefreshing = false
    }

    viewModel.loadingState.observe(viewLifecycleOwner) { state ->
      //        binding.swipeRefresh.isRefreshing = (state == LoadingState.LOADING)

      if (state == LoadingState.LOADING && adapter?.itemCount == 0) {
        binding.progressBar.visibility = View.VISIBLE
      } else {
        binding.progressBar.visibility = View.GONE
      }
    }

    binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }

    return binding.root
  }
}
