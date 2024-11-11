package com.example.applogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.applogin.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var app: UserApp
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as UserApp // Asegúrate de que esto esté correcto

        binding.buttonRegister.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                // Validar que la contraseña tenga más de 8 caracteres
                if (password.length <= 8) {
                    Toast.makeText(this, "La contraseña debe tener más de 8 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Validar que las contraseñas coincidan
                if (password != confirmPassword) {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    val user = app.room.userDao().getUser(username, password)

                    if (user == null) {
                        // Registrar nuevo usuario
                        val newUser = User(id = 0, name = username, password = password)
                        app.room.userDao().insert(newUser)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                            // Regresar a la actividad de inicio de sesión
                            finish() // Cierra esta actividad y regresa a MainActivity
                        }
                    } else {
                        // El usuario ya existe
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
