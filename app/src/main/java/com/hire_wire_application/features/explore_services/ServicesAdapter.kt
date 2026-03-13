package com.hire_wire_application.features.explore_services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hire_wire_application.databinding.ServicesListRowBinding
import com.hire_wire_application.models.db_models.Service

class ServicesAdapter(private var services: List<Service>) :
    RecyclerView.Adapter<ServiceRowViewHolder>() {

  fun updateServices(newServices: List<Service>) {
    this.services = newServices
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceRowViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ServicesListRowBinding.inflate(inflater, parent, false)

    return ServiceRowViewHolder(binding = binding)
  }

  override fun onBindViewHolder(holder: ServiceRowViewHolder, position: Int) {
    holder.bind(this.services[position])
  }

  override fun getItemCount(): Int = this.services.size
}
