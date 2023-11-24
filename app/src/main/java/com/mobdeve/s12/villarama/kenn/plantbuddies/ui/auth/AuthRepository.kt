package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth

import com.mobdeve.s12.villarama.kenn.plantbuddies.PlantBuddyDatabase

class AuthRepository(private val database: PlantBuddyDatabase) {

    suspend fun registerUser(username: String, password: String): Long {
        return try {
            database.userDao().insertUser(User(0, username, password))
        } catch (e: Exception) {
            -1
        }
    }

    suspend fun loginUser(username: String, password: String): Boolean {
        val user = database.userDao().checkUserCredentials(username, password)
        return user != null
    }

    suspend fun isUserLoggedIn(username: String, password: String): Boolean {
        val user = database.userDao().checkUserCredentials(username, password)
        return user != null
    }
    companion object {
        suspend fun isUserLoggedIn(
            database: PlantBuddyDatabase,
            username: String,
            password: String
        ): Boolean {
            val user = database.userDao().checkUserCredentials(username, password)
            return user != null
        }
    }
}
