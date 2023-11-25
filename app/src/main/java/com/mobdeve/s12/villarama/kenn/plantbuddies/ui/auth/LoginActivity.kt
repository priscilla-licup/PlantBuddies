package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.ActivityMainBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.gardenprofile.UsernameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var authRepository: AuthRepository
    private lateinit var binding: ActivityMainBinding
    private lateinit var usernameViewModel: UsernameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        // Inside your onCreate method
        usernameViewModel = ViewModelProvider(this).get(UsernameViewModel::class.java)

        val signUpButton: Button = findViewById(R.id.signuptab)
        signUpButton.setOnClickListener {
            // Handle "Sign up" button click
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        val signinButton: Button = findViewById(R.id.signintab)
        signinButton.setOnClickListener {
            // Handle "Sign in" button click
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        authRepository = AuthRepository(PlantBuddyDatabase.getDatabase(this))

        val usernameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.passwordfield)
        val signInButton: Button = findViewById(R.id.signinbut)

        signInButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val loginSuccessful = authRepository.loginUser(username, password)

                withContext(Dispatchers.Main) {
                    if (loginSuccessful) {

                        binding = ActivityMainBinding.inflate(layoutInflater)
                        usernameViewModel.username.value = username


                        setContentView(binding.root)
                        val navView: BottomNavigationView = binding.navView

                        val navController = findNavController(R.id.nav_host_fragment_activity_main)
                        // Passing each menu ID as a set of Ids because each
                        // menu should be considered as top level destinations.
                        val appBarConfiguration = AppBarConfiguration(setOf(
                            R.id.navigation_notes, R.id.navigation_reminder, R.id.navigation_gardenprofile))

                        setupActionBarWithNavController(navController, appBarConfiguration)
                        navView.setupWithNavController(navController)
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid login credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private suspend fun isUserLoggedIn(username: String, password: String): Boolean {
        return authRepository.isUserLoggedIn(username, password)
    }
}
