package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth.User
@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    fun checkUserCredentials(username: String, password: String): User?

    }
