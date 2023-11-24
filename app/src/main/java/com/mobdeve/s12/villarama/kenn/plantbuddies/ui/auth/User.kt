package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.auth

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
@Entity(tableName = "user_table")
data class User(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                @ColumnInfo(name = "username") val username: String,
                @ColumnInfo(name = "password") val password: String) {
}