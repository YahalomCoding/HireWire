package com.hire_wire_application.models

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.hire_wire_application.Completion

class FirebaseAuthModel {
  private var auth: FirebaseAuth = Firebase.auth

  fun signIn(email: String, password: String, context: Context, completion: Completion) {
    auth
        .signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { completion() }
        .addOnFailureListener {
          auth
              .createUserWithEmailAndPassword(email, password)
              .addOnSuccessListener { completion() }
              .addOnFailureListener {
                Toast.makeText(context, "Authentication Failed", Toast.LENGTH_LONG).show()
              }
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
