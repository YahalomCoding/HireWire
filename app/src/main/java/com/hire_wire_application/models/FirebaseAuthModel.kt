package com.hire_wire_application.models

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseAuthModel {
  private var auth: FirebaseAuth = Firebase.auth

  fun signIn(email: String, password: String, completion: () -> Unit) {
    auth
        .signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { completion() }
        .addOnFailureListener {
          auth
              .createUserWithEmailAndPassword(email, password)
              .addOnSuccessListener { completion() }
              .addOnFailureListener { Log.e("TAG", "Sign-In Failed: ${it.message}") }
        }
  }

  fun isUserLoggedIn(): Boolean {
    return auth.currentUser != null
  }

  fun signOut() {
    auth.signOut()
  }

  fun getLoggedInUserId(): String {
    return auth.currentUser?.uid ?: throw IllegalStateException("User Not Authenticated.")
  }
}
