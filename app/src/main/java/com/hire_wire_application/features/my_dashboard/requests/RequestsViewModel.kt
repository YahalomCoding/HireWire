package com.hire_wire_application.features.my_dashboard.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hire_wire_application.models.HireRequestWithDetails
import com.hire_wire_application.models.LoadingState
import com.hire_wire_application.models.Repository

class RequestsViewModel : ViewModel() {
  private val repository = Repository.shared

  val requests: LiveData<List<HireRequestWithDetails>> = repository.getRequestsToMe()
  val loadingState: LiveData<LoadingState> = repository.requestsLoadingState

  init {
    refresh()
  }

  fun refresh() {
    repository.refreshRequests()
  }
}
