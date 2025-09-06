package se.linerotech.module201.project6

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var textViewBmiResult: TextView
    private lateinit var textViewBmiLabel: TextView

    companion object {
        private const val UNDERWEIGHT_MAX = 18.5
        private const val NORMAL_MAX = 24.9
        private const val OVERWEIGHT_MAX = 29.9
        private const val CM_TO_M_CONVERSION = 100.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextWeight = findViewById(R.id.editTextWeight)
        editTextHeight = findViewById(R.id.editTextHeight)
        textViewBmiResult = findViewById(R.id.textViewResult)
        textViewBmiLabel = findViewById(R.id.textViewCategory)
        editTextWeight.doAfterTextChanged { calculateBmi() }
        editTextHeight.doAfterTextChanged { calculateBmi() }
    }
    private fun calculateBmi() {
        val weightText = editTextWeight.text.toString()
        val heightText = editTextHeight.text.toString()
        if (weightText.isBlank() || heightText.isBlank()) {
            textViewBmiResult.text = "0.0"
            textViewBmiLabel.text = "Your BMI Score"
            return
        }
        val weight = weightText.toDoubleOrNull()
        val heightCm = heightText.toDoubleOrNull()
        if (weight == null || heightCm == null || heightCm == 0.0) {
            textViewBmiResult.text = "0.0"
            textViewBmiLabel.text = "Invalid input"
            return
        }
        val heightM = heightCm / CM_TO_M_CONVERSION
        val bmi = weight / (heightM * heightM)
        val bmiRounded = String.format(Locale.US, "%.1f", bmi)
        textViewBmiResult.text = bmiRounded
        textViewBmiLabel.text = getBmiCategory(bmi)
    }

    private fun getBmiCategory(bmi: Double): String {
        return when {
            bmi < UNDERWEIGHT_MAX -> "Underweight"
            bmi <= NORMAL_MAX -> "Normal"
            bmi <= OVERWEIGHT_MAX -> "Overweight"
            else -> "Obese"
        }
    }
}
