package com.hire_wire_application.features.my_dashboard.requests

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
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

    val statusColorRes = when (request.status) {
      HireRequestStatus.PENDING -> R.color.status_pending_text
      HireRequestStatus.ACCEPTED -> R.color.status_accepted_text
      HireRequestStatus.REJECTED -> R.color.status_rejected_text
    }

    val statusBgRes = when (request.status) {
      HireRequestStatus.PENDING -> R.color.status_pending_bg
      HireRequestStatus.ACCEPTED -> R.color.status_accepted_bg
      HireRequestStatus.REJECTED -> R.color.status_rejected_bg
    }

    binding.requestStatus.setTextColor(ContextCompat.getColor(context, statusColorRes))
    binding.requestStatus.backgroundTintList = 
        ColorStateList.valueOf(ContextCompat.getColor(context, statusBgRes))

    if (request.status == HireRequestStatus.PENDING) {
      binding.requestActions.visibility = View.VISIBLE
      binding.actionDivider.visibility = View.VISIBLE
    } else {
      binding.requestActions.visibility = View.GONE
      binding.actionDivider.visibility = View.GONE
    }

    binding.btnAccept.setOnClickListener { onAcceptClick(request) }
    binding.btnReject.setOnClickListener { onRejectClick(request) }
  }
}
