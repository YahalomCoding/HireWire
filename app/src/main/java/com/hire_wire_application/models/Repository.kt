package com.hire_wire_application.models

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hire_wire_application.Completion
import com.hire_wire_application.UserCompletion
import com.hire_wire_application.models.CloudinaryStorageModel.ImagePathEnum
import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User

class Repository private constructor() {
  private val firebaseModel = FirebaseModel()
  private val firebaseAuth = FirebaseAuthModel()
  private val storageModel = StorageModel()
  private val localStorage: LocalStorageModel = LocalStorageModel()

  val servicesLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)

  companion object {
    val shared = Repository()
  }

  fun getHomeFeedServices(): LiveData<List<Service>> {
    return localStorage.getHomeFeedServices(firebaseAuth.getLoggedInUserId())
  }

  fun refreshHomeFeedServices() {
    servicesLoadingState.postValue(LoadingState.LOADING)
    val lastUpdated: Long = Service.lastUpdated

    firebaseModel.getAllServices(lastUpdated) { list ->
      var time = lastUpdated
      for (service in list) {
        localStorage.insertService(service)
        service.lastUpdated?.let { lastUpdatedService ->
          if (time < lastUpdatedService) {
            time = lastUpdatedService
          }
        }
      }
      Service.lastUpdated = time
      servicesLoadingState.postValue(LoadingState.LOADED)
    }
  }

  fun addService(service: Service, image: Bitmap, completion: Completion) {
    storageModel.uploadImage(image, service.id, ImagePathEnum.SERVICES) { imageUrl ->
      firebaseModel.addService(service.copy(imageUrl = imageUrl)) {
        refreshHomeFeedServices()
        completion()
      }
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
