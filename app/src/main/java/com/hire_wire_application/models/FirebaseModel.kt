package com.hire_wire_application.models

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hire_wire_application.Completion
import com.hire_wire_application.ServicesCompletion
import com.hire_wire_application.UserCompletion

class FirebaseModel {
  val db = Firebase.firestore
  val firebaseAuth = FirebaseAuthModel()

  private companion object COLLECTIONS {
    const val SERVICES = "services"
    const val USERS = "users"
  }

  fun getHomeFeedServices(completion: ServicesCompletion) {
    db.collection(SERVICES)
        .whereNotEqualTo("providerId", firebaseAuth.getLoggedInUserId())
        .get()
        .addOnSuccessListener { result -> completion(result.map { Service.fromJson(it.data) }) }
  }

  fun addService(service: Service, completion: Completion) {
    db.collection(SERVICES).document(service.id).set(service.toJson).addOnSuccessListener {
      completion()
    }
  }

  fun addUser(user: User, completion: Completion) {
    db.collection(USERS).document(user.id).set(user.toJson).addOnSuccessListener { completion() }
  }

  fun getUserById(userId: String, completion: UserCompletion) {
    db.collection(USERS).document(userId).get().addOnSuccessListener { result ->
      val foundUserDocument = result.data

      if (foundUserDocument != null) {
        completion(User.fromJson(foundUserDocument))
      } else {
        completion(null)
      }
    }
  }

  fun editUserById(userId: String, updatedData: Map<String, String>, completion: Completion) {
    db.collection(USERS).document(userId).update(updatedData).addOnSuccessListener { completion() }
  }
}
