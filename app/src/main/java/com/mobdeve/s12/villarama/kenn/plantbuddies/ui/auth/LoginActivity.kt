package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyDatabase
import com.mobdeve.s12.villarama.kenn.plantbuddies.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

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
