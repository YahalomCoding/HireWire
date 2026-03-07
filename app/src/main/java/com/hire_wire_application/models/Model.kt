package com.hire_wire_application.models

import android.graphics.Bitmap
import com.hire_wire_application.Completion
import com.hire_wire_application.ServicesCompletion

class Model private constructor() {
  private val firebaseModel = FirebaseModel()
  private val storageModel = StorageModel()

  companion object {
    val shared = Model()
  }

  fun getHomeFeedServices(completion: ServicesCompletion) {
    firebaseModel.getHomeFeedServices(completion)
  }

  fun addService(service: Service, image: Bitmap, completion: Completion) {
    storageModel.uploadServiceImage(image, service) { imageUrl ->
      firebaseModel.addService(service.copy(imageUrl = imageUrl), completion)
    }
  }
}
