package com.hire_wire_application.features.profile_page

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hire_wire_application.Completion
import com.hire_wire_application.models.FirebaseAuthModel
import com.hire_wire_application.models.LoadingState
import com.hire_wire_application.models.Repository
import com.hire_wire_application.models.db_models.User

class ProfileViewModel : ViewModel() {
  private val firebaseAuth = FirebaseAuthModel()
  private val repository = Repository.shared

  val userId: String = firebaseAuth.getLoggedInUserId()
  val user: LiveData<User> = repository.getUserById(userId)

  fun refresh() {
    repository.refreshUser(userId)
  }

  fun addUser(user: User, image: Bitmap, completion: Completion) {
    repository.addUser(user, image, completion)
  }

  fun editUserById(
      updatedData: Map<String, String>,
      updatedImage: Bitmap?,
      completion: Completion,
  ) {
    repository.editUserById(userId, updatedData, updatedImage, completion)
  }
}
