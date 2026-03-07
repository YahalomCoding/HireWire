package com.hire_wire_application.models

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hire_wire_application.Completion
import com.hire_wire_application.ServicesCompletion

class FirebaseModel {
  val db = Firebase.firestore
  val firebaseAuth = FirebaseAuthModel()

  private companion object COLLECTIONS {
    const val SERVICES = "services"
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
}
