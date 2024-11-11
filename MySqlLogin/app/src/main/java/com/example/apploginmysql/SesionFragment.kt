package com.example.apploginmysql

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class SesionFragment : Fragment() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var txtForgotPassword: TextView // Añadido para el botón de "Olvidé mi contraseña"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sesion, container, false)

        // Inicializa las vistas
        editTextUsername = view.findViewById(R.id.editTextUsername)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        btnLogin = view.findViewById(R.id.buttonLogin)
        btnRegister = view.findViewById(R.id.buttonRegister)
        txtForgotPassword = view.findViewById(R.id.buttonForgotPassword) // Asegúrate de que este ID coincide con el de tu layout

        btnLogin.setOnClickListener {
            iniciarSesion()
        }

        btnRegister.setOnClickListener {
            irARegistro()
        }

        // Configura el clic del botón "Olvidé mi contraseña"
        txtForgotPassword.setOnClickListener {
            irAOlvidarContrasena()
        }

        return view
    }

    private fun iniciarSesion() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Verifica si los campos están vacíos
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "http://192.168.56.1/login/login.php" // Cambia esta URL a tu script de login en PHP

        // Crear solicitud al servidor
        val request = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                // Manejar la respuesta del servidor
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    val message = jsonResponse.getString("message")

                    if (success) {
                        // Mensaje de bienvenida con el nombre de usuario
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        // Aquí puedes redirigir a otra actividad si es necesario
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error de conexión: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["user"] = username
                params["pwd"] = password
                return params
            }
        }

        // Añadir la solicitud a la cola de Volley
        Volley.newRequestQueue(requireContext()).add(request)
    }


    private fun irARegistro() {
        // Cambia a la actividad de registro
        val intent = Intent(requireContext(), RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun irAOlvidarContrasena() {
        val username = editTextUsername.text.toString()
        if (username.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingresa tu nombre de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        // Cambia a la actividad de "Olvidé mi contraseña" y pasa el nombre de usuario
        val intent = Intent(requireContext(), PwdActivity::class.java).apply {
            putExtra("USERNAME", username) // Pasar el nombre de usuario
        }
        startActivity(intent)
    }
}
