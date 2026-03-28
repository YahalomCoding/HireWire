package com.hire_wire_application.features.my_dashboard.my_services

import androidx.recyclerview.widget.RecyclerView
import com.hire_wire_application.databinding.MyServicesListRowBinding
import com.hire_wire_application.models.db_models.Service

class MyServiceRowViewHolder(
    private val binding: MyServicesListRowBinding,
    private val onEditClick: (Service) -> Unit,
    private val onDeleteClick: (Service) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(service: Service) {
    binding.serviceTitle.text = service.title
    binding.serviceSubtitle.text = service.description

    binding.editButton.setOnClickListener { onEditClick(service) }

    binding.deleteButton.setOnClickListener { onDeleteClick(service) }
  }
}
