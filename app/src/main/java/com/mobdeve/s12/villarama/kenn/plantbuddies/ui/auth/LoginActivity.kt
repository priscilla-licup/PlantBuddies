package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        // Inside your onCreate method

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
                        val binding = ActivityMainBinding.inflate(layoutInflater)
                        setContentView(binding.root)
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
