package com.hire_wire_application.features.my_dashboard.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hire_wire_application.databinding.FragmentRequestsBinding
import com.hire_wire_application.models.LoadingState
import com.hire_wire_application.models.Repository
import com.hire_wire_application.models.db_models.HireRequestStatus

class RequestsFragment : Fragment() {
  private lateinit var binding: FragmentRequestsBinding
  private val viewModel: RequestsViewModel by viewModels()
  private var adapter: RequestsAdapter? = null

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    binding = FragmentRequestsBinding.inflate(inflater, container, false)

    binding.requestsRecyclerView.setHasFixedSize(true)
    binding.requestsRecyclerView.layoutManager = LinearLayoutManager(context)

    adapter =
        RequestsAdapter(
            requests = emptyList(),
            onAcceptClick = { request ->
              Repository.shared.updateRequestStatus(request.id, HireRequestStatus.ACCEPTED) {
                viewModel.refresh()
              }
            },
            onRejectClick = { request ->
              Repository.shared.updateRequestStatus(request.id, HireRequestStatus.REJECTED) {
                viewModel.refresh()
              }
            },
        )
    binding.requestsRecyclerView.adapter = adapter

    viewModel.requests.observe(viewLifecycleOwner) { requests ->
      adapter?.updateRequests(requests)
      binding.requestsSwipeRefresh.isRefreshing = false
    }

    viewModel.loadingState.observe(viewLifecycleOwner) { loadingState ->
      binding.requestsSwipeRefresh.isRefreshing = (loadingState == LoadingState.LOADING)
    }

    binding.requestsSwipeRefresh.setOnRefreshListener { viewModel.refresh() }

    return binding.root
  }
}
