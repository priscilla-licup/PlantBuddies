package com.mobdeve.s12.villarama.kenn.plantbuddies

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mobdeve.s12.villarama.kenn.plantbuddies.databinding.ActivityMainBinding
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.AuthRepository
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.AuthRepository.Companion.isUserLoggedIn
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.LoginActivity
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder.AlarmReceiver.Companion.CHANNEL_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//kenny comment
class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.login)

        CoroutineScope(Dispatchers.Main).launch {
            if (!isUserLoggedIn()) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                setContentView(R.layout.login)
            } else {
                setContentView(binding.root)
                val navView: BottomNavigationView = binding.navView

                val navController = findNavController(R.id.nav_host_fragment_activity_main)
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_notes,
                        R.id.navigation_reminder,
                        R.id.navigation_gardenprofile
                    )
                )

                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)

            }
        }

    }

    private suspend fun isUserLoggedIn(): Boolean {
        val usernameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.passwordfield)
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        return AuthRepository.isUserLoggedIn(PlantBuddyDatabase.getDatabase(this), username, password)
    }

}