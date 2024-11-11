package com.example.apploginmysql

class User(
    private var user: String,
    private var pwd: String,
) {
    // Getter para la propiedad user
    fun getUser(): String {
        return user
    }

    // Setter para la propiedad user
    fun setUser(user: String) {
        this.user = user
    }

    // Getter para la propiedad pwd
    fun getPwd(): String {
        return pwd
    }

    // Setter para la propiedad pwd
    fun setPwd(pwd: String) {
        this.pwd = pwd
    }
}
