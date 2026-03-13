package com.hire_wire_application.features.my_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hire_wire_application.databinding.FragmentMyServicesBinding
import com.hire_wire_application.models.LoadingState

class MyServicesFragment : Fragment() {
    private lateinit var binding: FragmentMyServicesBinding
    private val viewModel: MyServicesViewModel by viewModels()
    private var adapter: MyServicesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyServicesBinding.inflate(inflater, container, false)

        binding.myServicesRecyclerView.setHasFixedSize(true)
        binding.myServicesRecyclerView.layoutManager = LinearLayoutManager(context)

        adapter = MyServicesAdapter(emptyList())
        binding.myServicesRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { services ->
            adapter?.updateServices(services)
            binding.myServicesSwipeRefresh.isRefreshing = false
        }

        binding.myServicesSwipeRefresh.setOnRefreshListener { viewModel.refresh() }

        return binding.root
    }
}