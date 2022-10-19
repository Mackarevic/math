package com.example.mathlearner.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mathlearner.R
import com.example.mathlearner.saves.SaveManager
import com.example.mathlearner.saves.SaveType
import com.example.mathlearner.utils.Utility
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val numberRegex = Regex("\\d+")

    private var strikes = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var randomNumber1 = Utility.getRandomInteger(0, 50)
        var randomNumber2 = Utility.getRandomInteger(0, 50)

        val textViewNumber1 = findViewById<TextView>(R.id.main_text_number1)
        val textViewNumber2 = findViewById<TextView>(R.id.main_text_number2)
        val userInput = findViewById<EditText>(R.id.main_user_input)
        val resultText = findViewById<TextView>(R.id.main_text_result)
        val btnNewTask = findViewById<Button>(R.id.main_task_new)
        val btnValidateTask = findViewById<Button>(R.id.main_task_check)
        val btnResultsLatest = findViewById<Button>(R.id.main_results_latest)

        val navBarTasks = findViewById<ImageView>(R.id.nav_bar_activity_tasks)
        val navBarCalc = findViewById<ImageView>(R.id.nav_bar_activity_calc)

        textViewNumber1.text = "$randomNumber1"
        textViewNumber2.text = "$randomNumber2"

        btnValidateTask.setOnClickListener {
            val validResult = randomNumber1 + randomNumber2

            if (userInput.text.toString()
                    .isBlank() || userInput.text.toString().isEmpty()
            ) {
                resultText.text = getString(R.string.main_results_blank)

                try {
                    SaveManager.writeSave(
                        applicationContext,
                        randomNumber1,
                        randomNumber2,
                        0,
                        SaveType.BLANK_FIELD
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                userInput.text = null

                strikes = 0

                randomNumber1 = Utility.getRandomInteger(1, 50)
                randomNumber2 = Utility.getRandomInteger(1, 50)

                textViewNumber1.text = "$randomNumber1"
                textViewNumber2.text = "$randomNumber2"

                return@setOnClickListener
            }

            if (!numberRegex.matches(userInput.text.toString())) {
                resultText.text = getString(R.string.common_regex_not_matched)

                strikes = 0

                randomNumber1 = Utility.getRandomInteger(1, 50)
                randomNumber2 = Utility.getRandomInteger(1, 50)

                textViewNumber1.text = "$randomNumber1"
                textViewNumber2.text = "$randomNumber2"

                return@setOnClickListener
            }

            if (userInput.text.toString().toInt() != validResult) {
                strikes = 0

                try {
                    SaveManager.writeSave(
                        applicationContext,
                        randomNumber1,
                        randomNumber2,
                        Utility.convertString(userInput.text.toString()),
                        SaveType.INCORRECT
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                resultText.text = getString(R.string.main_results_invalid)
                userInput.text = null

                randomNumber1 = Utility.getRandomInteger(1, 50)
                randomNumber2 = Utility.getRandomInteger(1, 50)

                textViewNumber1.text = "$randomNumber1"
                textViewNumber2.text = "$randomNumber2"

                return@setOnClickListener
            }

            strikes++
            if (strikes >= 2)
                resultText.text = getString(R.string.main_results_correct_strike).replace(
                    "%num%",
                    strikes.toString()
                )
            else
                resultText.text = getString(R.string.main_results_correct)

            try {
                SaveManager.writeSave(
                    applicationContext,
                    randomNumber1,
                    randomNumber2,
                    Utility.convertString(userInput.text.toString()),
                    SaveType.CORRECT
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }

            randomNumber1 = Utility.getRandomInteger(1, 50)
            randomNumber2 = Utility.getRandomInteger(1, 50)

            textViewNumber1.text = "$randomNumber1"
            textViewNumber2.text = "$randomNumber2"

            userInput.text = null
        }

        btnNewTask.setOnClickListener {
            try {
                SaveManager.writeSave(
                    applicationContext,
                    randomNumber1,
                    randomNumber2,
                    -1,
                    SaveType.NEW_TASK
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }

            randomNumber1 = Utility.getRandomInteger(1, 50)
            randomNumber2 = Utility.getRandomInteger(1, 50)

            textViewNumber1.text = "$randomNumber1"
            textViewNumber2.text = "$randomNumber2"

            Toast.makeText(
                applicationContext,
                getString(R.string.main_messages_new_task),
                Toast.LENGTH_SHORT
            ).show()
        }

        btnResultsLatest.setOnClickListener {
            startActivity(Intent(applicationContext, ResultListActivity::class.java))
        }

        navBarCalc.setOnClickListener {
            startActivity(Intent(applicationContext, CalculatorActivity::class.java))
        }

        navBarTasks.setOnClickListener {
            Toast.makeText(
                applicationContext,
                getString(R.string.common_nav_bar_here),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}