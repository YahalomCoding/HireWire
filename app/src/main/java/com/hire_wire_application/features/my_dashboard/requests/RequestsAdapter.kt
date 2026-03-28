package com.hire_wire_application.features.my_dashboard.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hire_wire_application.databinding.RequestsListRowBinding
import com.hire_wire_application.models.HireRequestWithDetails
import com.hire_wire_application.models.db_models.HireRequest

class RequestsAdapter(
    private var requests: List<HireRequestWithDetails>,
    private val onAcceptClick: (HireRequest) -> Unit,
    private val onRejectClick: (HireRequest) -> Unit,
) : RecyclerView.Adapter<RequestRowViewHolder>() {

  fun updateRequests(newRequests: List<HireRequestWithDetails>) {
    this.requests = newRequests
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestRowViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = RequestsListRowBinding.inflate(inflater, parent, false)
    return RequestRowViewHolder(binding, onAcceptClick, onRejectClick)
  }

  override fun onBindViewHolder(holder: RequestRowViewHolder, position: Int) {
    holder.bind(requests[position])
  }

  override fun getItemCount(): Int = requests.size
}
