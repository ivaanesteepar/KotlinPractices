package com.example.applogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.applogin.databinding.ActivityPwdBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPwd : AppCompatActivity() {
    private lateinit var app: UserApp
    private lateinit var binding: ActivityPwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as UserApp // Asegúrate de que esto esté correcto

        // Lógica para guardar la nueva contraseña
        binding.buttonSubmitNewPassword.setOnClickListener {
            val newPassword = binding.editTextNewPassword.text.toString()
            val username = intent.getStringExtra("USERNAME") ?: return@setOnClickListener

            if (newPassword.isNotEmpty()) {
                // Validar que la nueva contraseña tenga más de 8 caracteres
                if (newPassword.length <= 8) {
                    Toast.makeText(this, "La contraseña debe tener más de 8 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Lógica para actualizar la contraseña en la base de datos
                lifecycleScope.launch {
                    try {
                        app.room.userDao().updatePassword(username, newPassword)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ForgotPwd, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                            finish() // Cierra esta actividad y vuelve a la anterior
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ForgotPwd, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa una nueva contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
