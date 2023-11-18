package com.ketchupzz.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.ketchupzz.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var currentInput = StringBuilder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBackSpace.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput.deleteCharAt(currentInput.length - 1)
                binding.textView.text = currentInput
            }

        }


    }
    fun onButtonClick(view: View) {
        if (view is Button) {
            val value = view.text.toString()
            checkButtonValue(value)
        }
    }

    private fun checkButtonValue(value: String) {
        when (value) {
            "0" -> {
                if (currentInput.isEmpty() || (currentInput.length == 1 && currentInput[0].toString() == "0")) {
                    currentInput.clear()
                    currentInput.append("0")
                } else {
                    currentInput.append("0")
                }
            }
            "CE" -> {
                currentInput.clear()
            }
            "( )" -> {
                if (currentInput.isEmpty() || currentInput.endsWith("(")) {
                    currentInput.append("(")
                } else {
                    val lastCharIndex = currentInput.length - 1
                    if (lastCharIndex >= 0 && Character.isDigit(currentInput[lastCharIndex])) {
                        currentInput.append("(")
                    } else {
                        currentInput.append(")")
                    }
                }
            }

            "=" -> {
                calculateResult()
            }
            else -> {
                currentInput.append(value)
            }
        }

        binding.textView.text = currentInput.toString()
    }



    private fun calculateResult() {
        try {
            val expression = ExpressionBuilder(currentInput.toString()).build()
            val result = expression.evaluate()
            // Check if the result is a whole number
            val isWholeNumber = result % 1 == 0.0

            // Format the result accordingly
            if (isWholeNumber) {
                currentInput.clear()
                currentInput.append(result.toLong())
            } else {
                currentInput.clear()
                currentInput.append(result)
            }
        } catch (e: Exception) {
            Log.d("calc",e.message.toString())
            currentInput.clear()
            currentInput.append("Error")
        }
    }




}