package com.hire_wire_application

import androidx.recyclerview.widget.RecyclerView
import com.hire_wire_application.databinding.ServicesListRowBinding
import com.hire_wire_application.models.Service

class ServiceRowViewHolder(private val binding: ServicesListRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

  private lateinit var service: Service

  fun bind(service: Service) {
    this.service = service

    binding.serviceImage.setImageResource(R.drawable.profile_icon)
    binding.serviceTitle.text = service.title

    val context = binding.root.context
    binding.servicePrice.text = context.getString(R.string.price_format, service.price)
  }
}
