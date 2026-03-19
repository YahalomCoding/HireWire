package com.hire_wire_application.features.explore_services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hire_wire_application.R
import com.hire_wire_application.databinding.DialogHireServiceBinding
import com.hire_wire_application.models.Repository
import com.hire_wire_application.models.db_models.HireRequest
import com.hire_wire_application.models.db_models.HireRequestStatus
import com.hire_wire_application.models.db_models.Service
import com.squareup.picasso.Picasso
import java.util.UUID

class HireServiceDialogFragment(private val service: Service) : DialogFragment() {

  private var _binding: DialogHireServiceBinding? = null
  private val binding
    get() = _binding!!

  private var hasPendingRequest = false

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View {
    _binding = DialogHireServiceBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupUI()
    observePendingRequest()
  }

  private fun setupUI() {
    binding.hireServiceTitle.text = service.title
    binding.hireServiceDescription.text = service.description

    if (service.imageUrl.isNotEmpty() && service.imageUrl != "Initial Value") {
      Picasso.get().load(service.imageUrl).into(binding.hireServiceImage)
    }

    Repository.shared.getUserById(service.providerId).observe(viewLifecycleOwner) { user ->
      user?.let {
        binding.hireServiceProviderName.text = getString(R.string.provider_name_format, it.name)

        binding.confirmHireButton.setOnClickListener {
          if (hasPendingRequest) {
            Toast.makeText(context, getString(R.string.already_requested_toast), Toast.LENGTH_SHORT)
                .show()
          } else {
            sendHireRequest()
          }
        }
      }
    }
  }

  private fun observePendingRequest() {
    Repository.shared.getPendingRequestByMe(service.id).observe(viewLifecycleOwner) { request ->
      hasPendingRequest = request != null
    }
  }

  private fun sendHireRequest() {
    val newRequest =
        HireRequest(
            id = UUID.randomUUID().toString(),
            serviceId = service.id,
            requesterId = Repository.shared.getLoggedInUserId(),
            providerId = service.providerId,
            status = HireRequestStatus.PENDING,
        )

    Repository.shared.addRequest(newRequest) {
      Toast.makeText(context, getString(R.string.hire_request_success), Toast.LENGTH_SHORT).show()
      dismiss()
    }
  }

  override fun onStart() {
    super.onStart()
    dialog
        ?.window
        ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    const val TAG = "HireServiceDialogFragment"
  }
}
