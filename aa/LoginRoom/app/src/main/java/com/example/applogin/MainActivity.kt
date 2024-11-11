package com.example.applogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.applogin.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import android.content.Intent // Importar Intent al inicio del archivo

class MainActivity : AppCompatActivity() {

    private lateinit var app: UserApp
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as UserApp // Asegúrate de que esto esté correcto

        // Lógica para iniciar sesión
        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Validar que la contraseña tenga más de 8 caracteres
                if (password.length <= 8) {
                    Toast.makeText(this, "La contraseña debe tener más de 8 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Salir del listener si la contraseña no es válida
                }

                lifecycleScope.launch {
                    val user = app.room.userDao().getUser(username, password)

                    if (user != null) {
                        // Usuario existe, iniciar sesión
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Bienvenido, $username", Toast.LENGTH_SHORT).show()
                            // Aquí puedes iniciar la siguiente actividad
                        }
                    } else {
                        // El usuario no existe
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Lógica para ir a la actividad de registro
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java) // Asegúrate de que RegisterActivity esté definida
            startActivity(intent) // Inicia la actividad de registro
        }

        // Lógica para ir a la actividad de restablecimiento de contraseña
        binding.textViewForgotPassword.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            if (username.isNotEmpty()) {
                val intent = Intent(this, ForgotPwd::class.java)
                intent.putExtra("USERNAME", username) // Pasar el nombre de usuario a la nueva actividad
                startActivity(intent) // Inicia la actividad de restablecimiento de contraseña
            } else {
                Toast.makeText(this, "Por favor, ingresa tu nombre de usuario", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
