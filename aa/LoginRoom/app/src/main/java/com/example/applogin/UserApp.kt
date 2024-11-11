package com.example.applogin

import android.app.Application
import androidx.room.Room

class UserApp : Application() {
    // Usar `by lazy` para asegurar que la base de datos se inicializa solo una vez
    val room by lazy {
        Room.databaseBuilder(this, UserDb::class.java, "user_database")
            .fallbackToDestructiveMigration() // Opcional, permite eliminar y recrear la base de datos si hay un cambio en el esquema
            .build()
    }
}
