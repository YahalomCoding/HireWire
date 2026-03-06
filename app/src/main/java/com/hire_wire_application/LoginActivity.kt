package com.hire_wire_application

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hire_wire_application.databinding.ActivityLoginBinding
import com.hire_wire_application.models.FirebaseAuthModel

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  private val firebaseAuth = FirebaseAuthModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    binding.signInButton.setOnClickListener {
      val email = binding.emailInput.text.toString()
      val password = binding.passwordInput.text.toString()

      firebaseAuth.signIn(email, password) { navigateToMainActivity() }
    }
  }

  override fun onStart() {
    super.onStart()
    if (firebaseAuth.isUserLoggedIn()) {
      navigateToMainActivity()
    }
  }

  private fun navigateToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
  }
}
