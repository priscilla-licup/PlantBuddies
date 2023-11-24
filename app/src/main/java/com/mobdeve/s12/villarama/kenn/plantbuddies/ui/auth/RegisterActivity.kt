// RegisterActivity.kt
package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth

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
