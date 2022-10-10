package com.example.mathlearner
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var rnds1 = (0..10).random()
        var rnds2 = (0..10).random()

        var zahl = findViewById<TextView>(R.id.zahl1)
        var zahl2 = findViewById<TextView>(R.id.zahl2)
        var input = findViewById<EditText>(R.id.input)
        var result = findViewById<TextView>(R.id.result)
        var button = findViewById<MaterialButton>(R.id.check) as Button
        var newTask = findViewById<MaterialButton>(R.id.newTask) as Button


            zahl.setText("$rnds1")
            zahl2.setText("$rnds2")


        button.setOnClickListener {
            val ergebniss = rnds1 + rnds2
            val validInput = Regex("\\d+")
            if (input.text.toString().isBlank() || !validInput.matches(input.text.toString())) {
                result.set Text("Ungültige Angabe")
                input.setText("")
                return@setOnClickListener
            }
            if (input.text.toString().toInt() == ergebniss){
                result.setText("Angabe Richtig")
                 rnds1 = (1..10).random()
                 rnds2 = (1..10).random()
                 zahl.setText("$rnds1")
                 zahl2.setText("$rnds2")
                 input.setText("")
            } else {
                result.setText("Angabe Falsch")
                input.setText("")
            }
        }
            //no idea why this works but don´t touch a running system
        newTask.setOnClickListener{
         //   Toast.makeText(applicationContext, "Generating new Task...", Toast.LENGTH_SHORT).show()
            rnds1 = (1..10).random()
            rnds2 = (1..10).random()
            zahl.setText("$rnds1")
            zahl2.setText("$rnds2")
            Toast.makeText(applicationContext, "Done !", Toast.LENGTH_SHORT).show()
        }
    }
}