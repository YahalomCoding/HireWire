package com.hire_wire_application.features.my_dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hire_wire_application.databinding.MyServicesListRowBinding
import com.hire_wire_application.models.db_models.Service

class MyServicesAdapter(
    private var services: List<Service>,
    private val onEditClick: (Service) -> Unit,
) : RecyclerView.Adapter<MyServiceRowViewHolder>() {

  fun updateServices(newServices: List<Service>) {
    this.services = newServices
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyServiceRowViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = MyServicesListRowBinding.inflate(inflater, parent, false)
    return MyServiceRowViewHolder(binding, onEditClick)
  }

  override fun onBindViewHolder(holder: MyServiceRowViewHolder, position: Int) {
    holder.bind(services[position])
  }

  override fun getItemCount(): Int = services.size
}
