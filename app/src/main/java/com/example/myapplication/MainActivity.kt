package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        val buttons = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2", R.id.btn3 to "3",
            R.id.btn4 to "4", R.id.btn5 to "5", R.id.btn6 to "6", R.id.btn7 to "7",
            R.id.btn8 to "8", R.id.btn9 to "9", R.id.btnDecimal to ".",

            R.id.btnAdd to "+", R.id.btnSubtract to "-", R.id.btnMultiply to "*",
            R.id.btnDivide to "/"
        )

        buttons.forEach { (id, text) ->
            findViewById<Button>(id).setOnClickListener { onDigitOrOperator(text) }
        }

        // Asignar listeners a funciones especiales
        findViewById<Button>(R.id.btnAC).setOnClickListener { onClear() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqual() }
    }


    private fun onDigitOrOperator(input: String) {
        if (stateError) {
            tvResult.text = input
            stateError = false
        } else {
            if (tvResult.text == "0" && input != ".") {
                tvResult.text = input
            } else {
                tvResult.append(input)
            }
        }
        lastNumeric = input.first().isDigit() || input == "."
        lastDot = input == "."
    }


    private fun onClear() {
        tvResult.text = "0"
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = tvResult.text.toString()

            try {
                val expression = ExpressionBuilder(txt).build()
                val result = expression.evaluate()

                tvResult.text = result.toString()
                lastDot = tvResult.text.contains(".")
            } catch (ex: Exception) {
                tvResult.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}