package com.hire_wire_application.models

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.hire_wire_application.Completion
import com.hire_wire_application.ServicesCompletion
import com.hire_wire_application.UserCompletion
import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User

class FirebaseModel {
  val db = Firebase.firestore
  val firebaseAuth = FirebaseAuthModel()

  private companion object COLLECTIONS {
    const val SERVICES = "services"
    const val USERS = "users"
    const val TAG = "FirebaseModel"
  }

  fun getAllServices(lastUpdated: Long, completion: ServicesCompletion) {
    db.collection(SERVICES)
        .whereGreaterThanOrEqualTo(Service.LAST_UPDATED_KEY, Timestamp(lastUpdated / 1000, 0))
        .orderBy(Service.LAST_UPDATED_KEY)
        .get()
        .addOnSuccessListener { result -> completion(result.map { Service.fromJson(it.data) }) }
        .addOnFailureListener { exception ->
          Log.e(TAG, "Error getting services", exception)
          completion(listOf())
        }
  }

  fun addService(service: Service, completion: Completion) {
    db.collection(SERVICES)
        .document(service.id)
        .set(service.toJson)
        .addOnSuccessListener { completion() }
        .addOnFailureListener { exception ->
          Log.e(TAG, "Error adding service", exception)
          completion()
        }
  }

  fun updateService(service: Service, completion: Completion) {
    db.collection(SERVICES)
        .document(service.id)
        .update(service.toJson)
        .addOnSuccessListener { completion() }
        .addOnFailureListener { exception ->
          Log.e(TAG, "Error updating service", exception)
          completion()
        }
  }

  fun addUser(user: User, completion: Completion) {
    db.collection(USERS)
        .document(user.id)
        .set(user.toJson)
        .addOnSuccessListener { completion() }
        .addOnFailureListener { exception ->
          Log.e(TAG, "Error adding user", exception)
          completion()
        }
  }

  fun getUserById(userId: String, completion: UserCompletion) {
    db.collection(USERS)
        .document(userId)
        .get()
        .addOnSuccessListener { result ->
          val foundUserDocument = result.data
          completion(foundUserDocument?.let { User.fromJson(it) })
        }
        .addOnFailureListener { exception ->
          Log.e(TAG, "Error getting user by id", exception)
          completion(null)
        }
  }

  fun editUserById(userId: String, updatedData: Map<String, String>, completion: Completion) {
    db.collection(USERS)
        .document(userId)
        .update(updatedData)
        .addOnSuccessListener { completion() }
        .addOnFailureListener { exception ->
          Log.e(TAG, "Error editing user", exception)
          completion()
        }
  }
}
