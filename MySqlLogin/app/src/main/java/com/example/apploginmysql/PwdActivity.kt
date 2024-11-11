package com.example.apploginmysql

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class PwdActivity : AppCompatActivity() {
    private lateinit var editTextNewPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonSubmitNewPassword: Button
    private lateinit var username: String // Variable para almacenar el nombre de usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwd)

        // Inicializar las vistas
        editTextNewPassword = findViewById(R.id.editTextNewPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonSubmitNewPassword = findViewById(R.id.buttonSubmitNewPassword)

        // Obtener el nombre de usuario de los extras
        username = intent.getStringExtra("USERNAME") ?: ""

        // Configurar el clic del botón para guardar la nueva contraseña
        buttonSubmitNewPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val newPassword = editTextNewPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()
3
        // Validar que las contraseñas no estén vacías
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que las contraseñas coincidan
        if (newPassword != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // URL del servidor
        val url = "http://192.168.56.1/login/reset_password.php" // Cambia esta URL por la de tu script PHP

        // Crear el objeto JSON para enviar al servidor
        val jsonBody = JSONObject().apply {
            put("new_pwd", newPassword)
            put("user", username) // Usar el nombre de usuario recibido
        }

        Log.d("PwdActivity", "Enviando datos: $jsonBody") // Log para depurar

        val request = object : JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            { response ->
                Log.d("PwdActivity", "Response: $response") // Log para ver la respuesta completa
                val success = response.optBoolean("success", false)
                val message = response.optString("message", "Respuesta no válida") // Manejo de mensaje

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                if (success) {
                    finish() // Cierra la actividad si el cambio fue exitoso
                }
            },
            { error ->
                // Manejo del error de red
                Toast.makeText(this, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
                Log.e("PwdActivity", "Error: ${error.message}")
                error.networkResponse?.let { response ->
                    val errorString = String(response.data)
                    Log.e("PwdActivity", "Código de error: ${response.statusCode}")
                    Log.e("PwdActivity", "Error response: $errorString")
                } ?: Log.e("PwdActivity", "Error sin respuesta de red.")
            }
        )
        {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        // Agrega la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(request)
        Log.d("PwdActivity", "Username: $username") // Esto ya está en tu código
    }
}
