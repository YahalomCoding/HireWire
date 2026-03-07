package com.hire_wire_application.models

import android.graphics.Bitmap
import com.hire_wire_application.StringCompletion

class StorageModel {
  private val cloudinaryStorageModel = CloudinaryStorageModel()

  fun uploadImage(
      image: Bitmap,
      linkedObjectId: String,
      imagePath: CloudinaryStorageModel.ImagePathEnum,
      completion: StringCompletion,
  ) {
    cloudinaryStorageModel.uploadImage(image, linkedObjectId, imagePath, completion)
  }
}
