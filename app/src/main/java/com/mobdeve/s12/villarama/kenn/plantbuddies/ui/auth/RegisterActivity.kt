// RegisterActivity.kt
package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Import statements...

class RegisterActivity : AppCompatActivity() {

    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        val signUpButton: Button = findViewById(R.id.signuptab)
        signUpButton.setOnClickListener {
            // Handle "Sign up" button click
            val intent = Intent(this@RegisterActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        val signinButton: Button = findViewById(R.id.signintab)
        signinButton.setOnClickListener {
            // Handle "Sign in" button click
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        authRepository = AuthRepository(PlantBuddyDatabase.getDatabase(this))

        val usernameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.passwordfield)
        val registerButton: Button = findViewById(R.id.registerbut)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Use the authRepository to perform registration
            CoroutineScope(Dispatchers.IO).launch {
                val userId = authRepository.registerUser(username, password)
            }
        }
    }
}
//
