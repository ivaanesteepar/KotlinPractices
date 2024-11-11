package com.example.apploginmysql

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Response
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializa las vistas
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        btnRegister = findViewById(R.id.buttonRegister)

        // Inicializa la RequestQueue
        requestQueue = Volley.newRequestQueue(this)

        // Configura el clic del botón
        btnRegister.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()

        if (password.length < 8){
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica si las contraseñas coinciden
        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica si los campos están vacíos
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "http://192.168.56.1/login/register.php"

        // Parámetros para enviar al servidor
        val params = HashMap<String, String>()
        params["user"] = username
        params["pwd"] = password

        // Crear la solicitud POST
        val request = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                // Manejar la respuesta del servidor
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    val message = jsonResponse.getString("message")

                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    if (success) {
                        finish() // Cierra la actividad después de registrarse
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("RegisterActivity", "Error: ${error.message}")
                Toast.makeText(this, "Error en la solicitud: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }

        // Añade la solicitud a la cola
        requestQueue.add(request)
    }

}
