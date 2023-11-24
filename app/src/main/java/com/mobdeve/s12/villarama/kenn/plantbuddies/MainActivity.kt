package com.mobdeve.s12.villarama.kenn.plantbuddies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.ActivityMainBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.AuthRepository
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.LoginActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signUpButton: Button = findViewById(R.id.signuptab)
        signUpButton.setOnClickListener {
            // Start the RegisterActivity when the "Sign Up" button is clicked
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        val signinButton: Button = findViewById(R.id.signintab)
        signinButton.setOnClickListener {
            // Start the LoginActivity when the "Sign in" button is clicked
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        lifecycleScope.launch {
            checkUserLoggedIn()
        }
    }

    private suspend fun checkUserLoggedIn() {
        try {
            val isLoggedIn = withContext(Dispatchers.IO) {
                AuthRepository.isUserLoggedIn(
                    PlantBuddyDatabase.getDatabase(this@MainActivity),
                    getUsernameFromUI(),
                    getPasswordFromUI()
                )
            }

            if (!isLoggedIn) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish() // Optional: finish the current activity
            } else {
//                val binding = ActivityMainBinding.inflate(layoutInflater)
//                setContentView(binding.root)
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                val navView: BottomNavigationView = binding.navView

                val navController = findNavController(R.id.nav_host_fragment_activity_main)
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                val appBarConfiguration = AppBarConfiguration(setOf(
                    R.id.navigation_notes, R.id.navigation_reminder, R.id.navigation_gardenprofile))

                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
                // Your remaining UI initialization code here
            }
        } catch (e: Exception) {
            // Log the exception
            Log.e("MainActivity", "Error in checkUserLoggedIn", e)
        }
    }

    private suspend fun getUsernameFromUI(): String {
        return withContext(Dispatchers.Main) {
            val usernameEditText: EditText = findViewById(R.id.username)
            usernameEditText.text.toString()
        }
    }

    private suspend fun getPasswordFromUI(): String {
        return withContext(Dispatchers.Main) {
            val passwordEditText: EditText = findViewById(R.id.passwordfield)
            passwordEditText.text.toString()
        }
    }
}
