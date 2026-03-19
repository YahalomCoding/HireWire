package com.hire_wire_application.features.explore_services

import androidx.recyclerview.widget.RecyclerView
import com.hire_wire_application.R
import com.hire_wire_application.databinding.ServicesListRowBinding
import com.hire_wire_application.models.db_models.Service
import com.squareup.picasso.Picasso

class ServiceRowViewHolder(
    private val binding: ServicesListRowBinding,
    private val onHireClick: (Service) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(service: Service) {
    Picasso.get().load(service.imageUrl).into(binding.serviceImage)

    binding.serviceTitle.text = service.title

    val context = binding.root.context
    binding.servicePrice.text = context.getString(R.string.price_format, service.price)

    binding.hireButton.setOnClickListener { onHireClick(service) }
  }
}
