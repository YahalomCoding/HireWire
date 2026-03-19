package com.hire_wire_application.features.my_dashboard.requests

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hire_wire_application.R
import com.hire_wire_application.databinding.RequestsListRowBinding
import com.hire_wire_application.models.HireRequestWithDetails
import com.hire_wire_application.models.db_models.HireRequest
import com.hire_wire_application.models.db_models.HireRequestStatus

class RequestRowViewHolder(
    private val binding: RequestsListRowBinding,
    private val onAcceptClick: (HireRequest) -> Unit,
    private val onRejectClick: (HireRequest) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(item: HireRequestWithDetails) {
    val request = item.request
    val context = binding.root.context
    binding.requestServiceName.text =
        context.getString(R.string.request_service_format, item.serviceName)
    binding.requestRequesterName.text =
        context.getString(R.string.request_from_format, item.requesterName ?: request.requesterId)
    binding.requestStatus.text = request.status.name

    when (request.status) {
      HireRequestStatus.PENDING -> {
        binding.requestStatus.setBackgroundColor(
            context.getColor(android.R.color.holo_orange_light)
        )
        binding.requestActions.visibility = View.VISIBLE
      }
      HireRequestStatus.ACCEPTED -> {
        binding.requestStatus.setBackgroundColor(context.getColor(android.R.color.holo_green_light))
        binding.requestActions.visibility = View.GONE
      }
      HireRequestStatus.REJECTED -> {
        binding.requestStatus.setBackgroundColor(context.getColor(android.R.color.holo_red_light))
        binding.requestActions.visibility = View.GONE
      }
    }

    binding.btnAccept.setOnClickListener { onAcceptClick(request) }
    binding.btnReject.setOnClickListener { onRejectClick(request) }
  }
}
