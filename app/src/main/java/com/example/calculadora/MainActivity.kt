package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculadora.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Declarar la variable de binding
    private var num1 = BigDecimal.ZERO
    private var num2 = BigDecimal.ZERO
    private var operacion = SIN_OPERACION
    private var esperandoSegundoNumero = false // Para saber si estamos ingresando el segundo número

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inicializar el binding
        setContentView(binding.root) // Usar la raíz del binding

        binding.tvTextoA.text = "0" // Asignamos el 0 como valor default a la pantallita de la calculadora
        operacion = SIN_OPERACION

        // Configuración de los botones
        binding.button1.setOnClickListener { numberPressed("7") }
        binding.button2.setOnClickListener { numberPressed("8") }
        binding.button3.setOnClickListener { numberPressed("9") }
        binding.button4.setOnClickListener { operationPressed(DIVISION, " ÷ ") }
        binding.button5.setOnClickListener { numberPressed("4") }
        binding.button6.setOnClickListener { numberPressed("5") }
        binding.button7.setOnClickListener { numberPressed("6") }
        binding.button8.setOnClickListener { operationPressed(MULTIPLICACION, " × ") }
        binding.button9.setOnClickListener { numberPressed("1") }
        binding.button10.setOnClickListener { numberPressed("2") }
        binding.button11.setOnClickListener { numberPressed("3") }
        binding.button12.setOnClickListener { operationPressed(RESTA, " - ") }
        binding.button13.setOnClickListener { numberPressed("0") }
        binding.button14.setOnClickListener { numberPressed(".") }
        binding.button15.setOnClickListener { resolvePressed() }
        binding.button16.setOnClickListener { operationPressed(SUMA, " + ") }

        // Modificación para el botón de borrado total
        binding.buttonClear.setOnClickListener { clearAll() }

        // Modificación para el botón de borrado de un solo dígito
        binding.buttonDelete.setOnClickListener { deleteLastDigit() }

    }

    // Función que se ejecuta cuando se pulsa cualquier número
    private fun numberPressed(num: String) {
        if (esperandoSegundoNumero) {
            // Si estamos esperando el segundo número, agregamos el nuevo número al texto actual
            binding.tvTextoA.text = "${binding.tvTextoA.text.trim()}  $num" // Agrega el segundo número con espacio adicional
            esperandoSegundoNumero = false // Ahora hemos ingresado el segundo número
        } else {
            if (binding.tvTextoA.text == "0" && num != ".") {
                binding.tvTextoA.text = num
            } else {
                binding.tvTextoA.text = "${binding.tvTextoA.text}$num"
            }
        }

        // Actualizar num1 o num2 según el estado de la operación
        if (operacion == SIN_OPERACION) {
            num1 = BigDecimal(binding.tvTextoA.text.toString().trim().substringBefore(' ')) // Solo el primer número
        } else {
            // Actualizamos num2 si hay un operador presente
            num2 = BigDecimal(binding.tvTextoA.text.toString().trim().substringAfterLast(' ')) // Solo el segundo número
        }
    }

    // Función que se ejecuta cuando se pulsa cualquier operador
    private fun operationPressed(operacion: Int, operador: String) {
        if (this.operacion != SIN_OPERACION) {
            // Si ya hay una operación en curso, resolvemos antes de continuar
            resolvePressed()
        } else {
            // Solo asigna num1 si no hay operación en curso (es decir, es la primera operación)
            num1 = BigDecimal(binding.tvTextoA.text.toString().trim()) // Obtener el número completo mostrado en la pantalla
        }

        this.operacion = operacion

        // Agregar el operador a la pantalla con un espacio adicional
        binding.tvTextoA.text = "$num1 $operador  " // Mostrar el primer número y el operador con espacio

        // Preparar para el siguiente número
        esperandoSegundoNumero = true // Indicar que estamos esperando el segundo número
    }


    // Función para resolver la operación
    private fun resolvePressed() {
        // Solo calcular si hay un segundo número
        if (esperandoSegundoNumero) {
            num2 = BigDecimal(binding.tvTextoA.text.toString().trim().substringAfterLast(' ')) // Obtener el segundo número
        }

        val result = when (operacion) {
            SUMA -> num1.add(num2)
            RESTA -> num1.subtract(num2)
            MULTIPLICACION -> num1.multiply(num2)
            DIVISION -> if (num2 != BigDecimal.ZERO) {
                num1.divide(num2, 10, RoundingMode.HALF_UP)
            } else {
                BigDecimal.ZERO
            }
            else -> BigDecimal.ZERO
        }

        // Mostrar el resultado en la pantalla
        binding.tvTextoA.text = if (result.stripTrailingZeros().scale() <= 0) {
            result.toBigInteger().toString()
        } else {
            result.setScale(2, RoundingMode.HALF_UP).toString()
        }

        // Resetear variables
        num2 = BigDecimal.ZERO // num2 se vuelve a 0
        esperandoSegundoNumero = false // Reseteamos para la próxima operación
        operacion = SIN_OPERACION // Reseteamos la operación para permitir una nueva entrada
    }

    // Función para borrar todo
    private fun clearAll() {
        binding.tvTextoA.text = "0" // Restablecer a 0
        num1 = BigDecimal.ZERO // Resetear num1
        num2 = BigDecimal.ZERO // Resetear num2
        operacion = SIN_OPERACION // Restablecer operación
        esperandoSegundoNumero = false // Resetear la espera del segundo número
    }

    // Función para borrar el último dígito
    private fun deleteLastDigit() {
        val currentText = binding.tvTextoA.text.toString()
        if (currentText.length > 1) {
            binding.tvTextoA.text = currentText.substring(0, currentText.length - 1)
        } else {
            binding.tvTextoA.text = "0" // Si queda vacío, poner "0"
        }

        // Actualizar los números
        if (operacion == SIN_OPERACION) {
            num1 = BigDecimal(binding.tvTextoA.text.toString().trim()) // Usar trim para evitar espacios
        } else {
            num2 = BigDecimal(binding.tvTextoA.text.toString().trim()) // Usar trim para evitar espacios
        }
    }

    companion object {
        const val SUMA = 1
        const val RESTA = 2
        const val MULTIPLICACION = 3
        const val DIVISION = 4
        const val SIN_OPERACION = 0
    }
}
