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
        binding.button4.setOnClickListener { operationPressed(DIVISION) }
        binding.button5.setOnClickListener { numberPressed("4") }
        binding.button6.setOnClickListener { numberPressed("5") }
        binding.button7.setOnClickListener { numberPressed("6") }
        binding.button8.setOnClickListener { operationPressed(MULTIPLICACION) }
        binding.button9.setOnClickListener { numberPressed("1") }
        binding.button10.setOnClickListener { numberPressed("2") }
        binding.button11.setOnClickListener { numberPressed("3") }
        binding.button12.setOnClickListener { operationPressed(RESTA) }
        binding.button13.setOnClickListener { numberPressed("0") }
        binding.button14.setOnClickListener { numberPressed(".") }
        binding.button15.setOnClickListener { resolvePressed() }
        binding.button16.setOnClickListener { operationPressed(SUMA) }

        // Modificación para el botón de borrado total
        binding.buttonClear.setOnClickListener { clearAll() }

        // Modificación para el botón de borrado de un solo dígito
        binding.buttonDelete.setOnClickListener { deleteLastDigit() }
    }

    // Función que se ejecuta cuando se pulsa cualquier numero
    private fun numberPressed(num: String) {
        if (binding.tvTextoA.text == "0" && num != ".") {
            binding.tvTextoA.text = num
        } else {
            binding.tvTextoA.text = "${binding.tvTextoA.text}$num"
        }

        if (operacion == SIN_OPERACION) {
            num1 = BigDecimal(binding.tvTextoA.text.toString())
        } else {
            num2 = BigDecimal(binding.tvTextoA.text.toString())
        }
    }

    // Función que se ejecuta cuando se pulsa cualquier operador
    private fun operationPressed(operacion: Int) {
        this.operacion = operacion
        num1 = BigDecimal(binding.tvTextoA.text.toString())
        binding.tvTextoA.text = "0"
    }

    private fun resolvePressed() {
        val result = when (operacion) { //se asignará a la variable result el resultado de la operación
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

        //actualizamos la pantallita de la calculadora
        binding.tvTextoA.text = if (result.stripTrailingZeros().scale() <= 0) {
            result.toBigInteger().toString()
        } else {
            result.setScale(2, RoundingMode.HALF_UP).toString()
        }

        num1 = result //si queremos hacer otra operación con el resultado, se lo asignamos a num1
        num2 = BigDecimal.ZERO //num2 se vuelve a 0
    }

    // Función para borrar todo
    private fun clearAll() {
        binding.tvTextoA.text = "0" // Restablecer a 0
        num1 = BigDecimal.ZERO // Resetear num1
        num2 = BigDecimal.ZERO // Resetear num2
        operacion = SIN_OPERACION // Restablecer operación
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
            num1 = BigDecimal(binding.tvTextoA.text.toString())
        } else {
            num2 = BigDecimal(binding.tvTextoA.text.toString())
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
