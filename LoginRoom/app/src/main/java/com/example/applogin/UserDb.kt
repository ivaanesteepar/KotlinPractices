package com.example.applogin

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDb: RoomDatabase() {
    abstract fun userDao(): UserDao
}