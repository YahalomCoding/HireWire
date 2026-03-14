package com.hire_wire_application.features.my_dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hire_wire_application.models.LoadingState
import com.hire_wire_application.models.Repository
import com.hire_wire_application.models.db_models.Service

class MyServicesViewModel : ViewModel() {

  val data: LiveData<List<Service>> = Repository.shared.getMyServices()
  val servicesLoadingState: LiveData<LoadingState> = Repository.shared.servicesLoadingState

  init {
    Repository.shared.refreshServices()
  }

  fun refresh() {
    Repository.shared.refreshServices()
  }
}
