package com.example.bytewisdom.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "users",
    indices = [Index(value = ["username"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val passwordHash: String,
    val salt: String,
    val createdAt: Long = System.currentTimeMillis()
)
