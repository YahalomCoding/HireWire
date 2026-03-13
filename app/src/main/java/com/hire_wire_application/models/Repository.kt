package com.hire_wire_application.models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hire_wire_application.Completion
import com.hire_wire_application.models.CloudinaryStorageModel.ImagePathEnum
import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User

class Repository private constructor() {
  private val firebaseModel = FirebaseModel()
  private val firebaseAuth = FirebaseAuthModel()
  private val storageModel = StorageModel()
  private val localStorage: LocalStorageModel = LocalStorageModel()

  val servicesLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
  val userLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)

  companion object {
    val shared = Repository()
  }

  fun getHomeFeedServices(): LiveData<List<Service>> {
    return localStorage.getHomeFeedServices(firebaseAuth.getLoggedInUserId())
  }

  fun getMyServices(): LiveData<List<Service>> {
    return localStorage.getMyServices(firebaseAuth.getLoggedInUserId())
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
      firebaseModel.addUser(user.copy(imageUrl = imageUrl)) {
        refreshUser(user.id)
        completion()
      }
    }
  }

  fun getUserById(userId: String): LiveData<User> {
    // Since it's a single user always try to refresh it in case it was updated
    // elsewhere
    refreshUser(userId)
    return localStorage.getUserById(userId)
  }

  fun refreshUser(userId: String) {
    userLoadingState.postValue(LoadingState.LOADING)
    firebaseModel.getUserById(userId) { user ->
      if (user != null) {
        localStorage.insertUser(user)
      }
      userLoadingState.postValue(LoadingState.LOADED)
    }
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
        firebaseModel.editUserById(userId, updatedDataWithImageUrl) {
          refreshUser(userId)
          completion()
        }
      }
    } else {
      firebaseModel.editUserById(userId, updatedData) {
        refreshUser(userId)
        completion()
      }
    }
  }
}