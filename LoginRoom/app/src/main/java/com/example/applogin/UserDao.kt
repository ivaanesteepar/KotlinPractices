package com.example.applogin

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getById(id: Int): User

    @Query("SELECT * FROM User WHERE name = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): User?

    @Update
    suspend fun update(user: User)

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

    @Query("UPDATE User SET password = :newPassword WHERE name = :username")
    suspend fun updatePassword(username: String, newPassword: String)


}