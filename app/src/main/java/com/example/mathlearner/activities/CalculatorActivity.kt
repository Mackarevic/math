package com.example.mathlearner.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mathlearner.R
import com.example.mathlearner.calculator.CalculationMethod
import com.example.mathlearner.calculator.CalculatorUtil

class CalculatorActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val input0 = findViewById<Button>(R.id.calculator_input_0)
        val input1 = findViewById<Button>(R.id.calculator_input_1)
        val input2 = findViewById<Button>(R.id.calculator_input_2)
        val input3 = findViewById<Button>(R.id.calculator_input_3)
        val input4 = findViewById<Button>(R.id.calculator_input_4)
        val input5 = findViewById<Button>(R.id.calculator_input_5)
        val input6 = findViewById<Button>(R.id.calculator_input_6)
        val input7 = findViewById<Button>(R.id.calculator_input_7)
        val input8 = findViewById<Button>(R.id.calculator_input_8)
        val input9 = findViewById<Button>(R.id.calculator_input_9)
        val inputAdd = findViewById<Button>(R.id.calculator_input_add)
        val inputMultiply = findViewById<Button>(R.id.calculator_input_multiply)
        val inputSubtract = findViewById<Button>(R.id.calculator_input_subtract)
        val inputDivide = findViewById<Button>(R.id.calculator_input_divide)
        val inputResult = findViewById<Button>(R.id.calculator_input_equal)
        val clear = findViewById<Button>(R.id.calculator_input_clear)
        val clearE = findViewById<Button>(R.id.calculator_input_clear_entry)
        val textView = findViewById<TextView>(R.id.calculator_text)

        input0.setOnClickListener {
            CalculatorUtil.addNumber(0, textView)
        }
        input1.setOnClickListener {
            CalculatorUtil.addNumber(1, textView)
        }
        input2.setOnClickListener {
            CalculatorUtil.addNumber(2, textView)
        }
        input3.setOnClickListener {
            CalculatorUtil.addNumber(3, textView)
        }
        input4.setOnClickListener {
            CalculatorUtil.addNumber(4, textView)
        }
        input5.setOnClickListener {
            CalculatorUtil.addNumber(5, textView)
        }
        input6.setOnClickListener {
            CalculatorUtil.addNumber(6, textView)
        }
        input7.setOnClickListener {
            CalculatorUtil.addNumber(7, textView)
        }
        input8.setOnClickListener {
            CalculatorUtil.addNumber(8, textView)
        }
        input9.setOnClickListener {
            CalculatorUtil.addNumber(9, textView)
        }

        clearE.setOnClickListener {
            if (textView.text.toString().length == 1) {
                textView.text = "0"
                return@setOnClickListener
            }
            textView.text = textView.text.toString().substring(0, textView.text.toString().length - 1)
        }
        clear.setOnClickListener {
            CalculatorUtil.clearOut(textView)
        }

        inputAdd.setOnClickListener {
            CalculatorUtil.addCalculationMethod(CalculationMethod.ADD, textView)
        }
        inputSubtract.setOnClickListener {
            CalculatorUtil.addCalculationMethod(CalculationMethod.SUBTRACT, textView)
        }
        inputDivide.setOnClickListener {
            CalculatorUtil.addCalculationMethod(CalculationMethod.DIVIDE, textView)
        }
        inputMultiply.setOnClickListener {
            CalculatorUtil.addCalculationMethod(CalculationMethod.MULTIPLY, textView)
        }
        inputResult.setOnClickListener {
            CalculatorUtil.calculate(textView) {
                textView.text = it.toString()
            }
        }

        val navBarTasks = findViewById<ImageView>(R.id.nav_bar_activity_tasks)
        val navBarCalc = findViewById<ImageView>(R.id.nav_bar_activity_calc)

        navBarCalc.setOnClickListener {
            Toast.makeText(applicationContext, getString(R.string.common_nav_bar_here), Toast.LENGTH_SHORT).show()
        }

        navBarTasks.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

}