package com.hire_wire_application

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.hire_wire_application.databinding.ActivityMainBinding
import com.hire_wire_application.models.FirebaseAuthModel

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController
  private val firebaseAuth = FirebaseAuthModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    binding.logOutButton.setOnClickListener {
      firebaseAuth.signOut()

      val intent = Intent(this, LoginActivity::class.java)
      startActivity(intent)
      finish()
    }

    setSupportActionBar(binding.topToolbar)
    setupTopToolbar()
    setupNavToolbar()
  }

  private fun setupTopToolbar() {
    val navHost = supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
    navController = navHost.navController
    NavigationUI.setupActionBarWithNavController(this, navController)
  }

  private fun setupNavToolbar() {
    binding.homeIcon.setOnClickListener {
      if (navController.currentDestination?.id != R.id.exploreServicesFragment) {
        navController.navigate(R.id.action_global_exploreServicesFragment)
      }
    }

    binding.menuIcon.setOnClickListener {
      if (navController.currentDestination?.id != R.id.myDashboardFragment) {
        navController.navigate(R.id.action_global_myDashboardFragment)
      }
    }

    binding.addIcon.setOnClickListener {
      if (navController.currentDestination?.id != R.id.createServiceFragment) {
        navController.navigate(R.id.action_global_createServiceFragment)
      }
    }

    binding.profileIcon.setOnClickListener {
      if (navController.currentDestination?.id != R.id.profilePageFragment) {
        navController.navigate(R.id.action_global_profilePageFragment)
      }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp()
  }
}
