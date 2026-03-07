package com.hire_wire_application.models

import android.graphics.Bitmap
import com.hire_wire_application.Completion
import com.hire_wire_application.ServicesCompletion
import com.hire_wire_application.UserCompletion
import com.hire_wire_application.models.CloudinaryStorageModel.ImagePathEnum

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
    storageModel.uploadImage(image, service.id, ImagePathEnum.SERVICES) { imageUrl ->
      firebaseModel.addService(service.copy(imageUrl = imageUrl), completion)
    }
  }

  fun addUser(user: User, image: Bitmap, completion: Completion) {
    storageModel.uploadImage(image, user.id, ImagePathEnum.USERS) { imageUrl ->
      firebaseModel.addUser(user.copy(imageUrl = imageUrl), completion)
    }
  }

  fun getUserById(userId: String, completion: UserCompletion) {
    firebaseModel.getUserById(userId, completion)
  }

  fun editUserById(
      userId: String,
      updatedData: Map<String, String>,
      updatedImage: Bitmap?,
      completion: Completion,
  ) {
    if (updatedImage != null) {
      storageModel.uploadImage(updatedImage, userId, ImagePathEnum.USERS) { imageUrl ->
        val updatedDataWithImageUrl = updatedData + ("imageUrl" to imageUrl)
        firebaseModel.editUserById(userId, updatedDataWithImageUrl, completion)
      }
    } else {
      firebaseModel.editUserById(userId, updatedData, completion)
    }
  }
}
