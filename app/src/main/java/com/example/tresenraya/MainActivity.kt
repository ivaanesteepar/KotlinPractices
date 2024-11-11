package com.example.tresenraya

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tresenraya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentPlayer = "X" // Jugador inicial
    private var board = Array(3) { arrayOfNulls<String>(3) } // Tablero 3x3
    private var gameActive = true // Controla el estado del juego
    private var playerXWins = 0 // Contador de victorias para jugador X
    private var playerOWins = 0 // Contador de victorias para jugador O

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar los botones del tablero
        val buttons = arrayOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        for (i in buttons.indices) {
            buttons[i].setOnClickListener { onCellClick(it as Button, i) }
        }

        // Configurar el botón de reinicio
        binding.resetButton.setOnClickListener { resetGame() }
    }

    private fun onCellClick(button: Button, index: Int) {
        val row = index / 3
        val col = index % 3

        // Comprobar si la celda está vacía y el juego está activo
        if (board[row][col] == null && gameActive) {
            board[row][col] = currentPlayer
            button.text = currentPlayer
            if (checkWinner()) { // hay 1 ganador
                showWinnerDialog()
                gameActive = false
            } else if (isBoardFull()) { // el tablero está lleno
                Toast.makeText(this, "¡Empate!", Toast.LENGTH_SHORT).show()
                gameActive = false
            } else { // alternar jugador
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    // Función para comprobar si hay un ganador
    private fun checkWinner(): Boolean {
        // Comprobar filas, columnas y diagonales
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    // Función para comprobar si el tablero está lleno
    private fun isBoardFull(): Boolean {
        for (row in board) {
            if (row.contains(null)) return false
        }
        return true
    }

    // Función para mostrar un diálogo con el ganador
    private fun showWinnerDialog() {
        // Mostrar un diálogo con el ganador
        val winner = if (currentPlayer == "X") {
            playerXWins++ // Incrementar contador de X
            "Jugador 1 (X) gana!"
        } else {
            playerOWins++ // Incrementar contador de O
            "Jugador 2 (O) gana!"
        }

        val message = "$winner\n" // Mensaje con el ganador

        AlertDialog.Builder(this)
            .setTitle("¡Ganador!")
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun resetGame() {
        // Reiniciar el juego
        board = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer = "X"
        gameActive = true
        val buttons = arrayOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )
        for (button in buttons) {
            button.text = ""
        }

        // Actualizar los contadores en los TextView
        binding.playerXCount.text = "Victorias Jugador 1 (X): $playerXWins"
        binding.playerOCount.text = "Victorias Jugador 2 (O): $playerOWins"
    }
}
