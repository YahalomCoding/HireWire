package com.hire_wire_application.models

import android.graphics.Bitmap
import com.hire_wire_application.StringCompletion

class StorageModel {
  private val cloudinaryStorageModel = CloudinaryStorageModel()

  fun uploadServiceImage(image: Bitmap, service: Service, completion: StringCompletion) {
    cloudinaryStorageModel.uploadServiceImage(image, service, completion)
  }
}
