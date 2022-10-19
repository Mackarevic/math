package com.example.mathlearner.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mathlearner.R
import com.example.mathlearner.utils.FileUtil

class CalculatorActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

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