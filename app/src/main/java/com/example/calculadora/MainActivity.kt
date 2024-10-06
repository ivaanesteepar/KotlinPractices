package com.example.calculadora

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var num1 = BigDecimal.ZERO
    private var num2 = BigDecimal.ZERO
    private var operacion = SIN_OPERACION
    private var esperandoSegundoNumero = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTextoA.text = "0"
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
        binding.button17.setOnClickListener { operationPressed(LOG, " log ") }
        binding.button18.setOnClickListener { operationPressed(LN, " ln ") }
        binding.button19.setOnClickListener { operationPressed(SIN, " sin ") }
        binding.button20.setOnClickListener { operationPressed(COS, " cos ") }
        binding.button21.setOnClickListener { operationPressed(TAN, " tan ") }
        binding.button22.setOnClickListener { operationPressed(RAIZ, " √ ") }
        binding.button23.setOnClickListener { operationPressed(CUADRADO, " ^2 ") }
        binding.button24.setOnClickListener { operationPressed(EXP, " ^ ") }

        // Botón para borrar todo
        binding.buttonClear.setOnClickListener { clearAll() }

        // Botón para borrar un dígito
        binding.buttonDelete.setOnClickListener { deleteLastDigit() }
    }

    private fun numberPressed(num: String) {
        // Si estamos esperando el segundo número
        if (esperandoSegundoNumero) {
            // Actualizamos el segundo número (num2)
            binding.tvTextoA.text = "${binding.tvTextoA.text}$num"
        } else {
            // Actualizamos el primer número (num1)
            if (binding.tvTextoA.text == "0" && num != ".") {
                binding.tvTextoA.text = num
            } else {
                binding.tvTextoA.text = "${binding.tvTextoA.text}$num"
            }
        }

        // Actualizamos num1 o num2 según la operación seleccionada
        try {
            if (operacion == SIN_OPERACION) {
                num1 = BigDecimal(binding.tvTextoA.text.toString().trim())
            } else {
                num2 = BigDecimal(binding.tvTextoA.text.toString().trim())
            }
        } catch (e: NumberFormatException) {
            Log.e("Calculadora", "Error en la conversión del número: ${e.message}")
        }
    }

    private fun operationPressed(operacion: Int, operador: String) {
        // Si hay una operación previa, resolverla primero
        if (this.operacion != SIN_OPERACION) {
            resolvePressed()
        } else {
            num1 = BigDecimal(binding.tvTextoA.text.toString().trim())
        }

        // Actualiza el operador actual
        this.operacion = operacion

        // En caso de operaciones científicas, ejecutamos inmediatamente
        if (operacion in listOf(LOG, LN, SIN, COS, TAN, RAIZ, CUADRADO)) {
            resolvePressed()  // Resolver inmediatamente
        } else {
            // Mostrar num1 y el nuevo operador (para el exponente)
            binding.tvTextoA.text = "$num1 $operador "
            esperandoSegundoNumero = true
        }
    }

    private fun resolvePressed() {
        try {
            if (esperandoSegundoNumero) {
                num2 = BigDecimal(binding.tvTextoA.text.toString().trim().substringAfterLast(' '))
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
                LOG -> BigDecimal(log10(num1.toDouble()))
                LN -> BigDecimal(ln(num1.toDouble()))
                SIN -> BigDecimal(sin(Math.toRadians(num1.toDouble())))
                COS -> BigDecimal(cos(Math.toRadians(num1.toDouble())))
                TAN -> BigDecimal(tan(Math.toRadians(num1.toDouble())))
                RAIZ -> BigDecimal(sqrt(num1.toDouble()))
                CUADRADO -> num1.multiply(num1)
                EXP -> {
                    try {
                        BigDecimal(Math.pow(num1.toDouble(), num2.toDouble()))
                    } catch (e: Exception) {
                        Log.e("Calculadora", "Error en la operación de exponente: ${e.message}")
                        BigDecimal.ZERO
                    }
                }
                else -> BigDecimal.ZERO
            }

            // Actualiza num1 con el resultado para operaciones encadenadas
            num1 = result

            // Muestra el resultado en la pantalla
            binding.tvTextoA.text = if (result.stripTrailingZeros().scale() <= 0) {
                result.toBigInteger().toString()
            } else {
                result.setScale(8, RoundingMode.HALF_UP).toString() // Cambiar a 8 decimales
            }

            num2 = BigDecimal.ZERO
            esperandoSegundoNumero = false
            operacion = SIN_OPERACION

        } catch (e: Exception) {
            Log.e("Calculadora", "Error en la resolución de la operación: ${e.message}")
            // Opcionalmente, puedes mostrar un mensaje al usuario
        }
    }

    private fun clearAll() {
        binding.tvTextoA.text = "0"
        num1 = BigDecimal.ZERO
        num2 = BigDecimal.ZERO
        operacion = SIN_OPERACION
        esperandoSegundoNumero = false
    }

    private fun deleteLastDigit() {
        val currentText = binding.tvTextoA.text.toString()
        if (currentText.length > 1) {
            binding.tvTextoA.text = currentText.substring(0, currentText.length - 1)
        } else {
            binding.tvTextoA.text = "0"
        }

        if (operacion == SIN_OPERACION) {
            num1 = BigDecimal(binding.tvTextoA.text.toString().trim())
        } else {
            num2 = BigDecimal(binding.tvTextoA.text.toString().trim())
        }
    }

    companion object {
        const val SUMA = 1
        const val RESTA = 2
        const val MULTIPLICACION = 3
        const val DIVISION = 4
        const val LOG = 5
        const val LN = 6
        const val SIN = 7
        const val COS = 8
        const val TAN = 9
        const val RAIZ = 10
        const val CUADRADO = 11
        const val EXP = 12
        const val SIN_OPERACION = 0
    }
}
