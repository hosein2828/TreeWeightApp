package com.example.treeweight

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.PI
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etEnvBelow = findViewById<EditText>(R.id.etEnvBelow)
        val etEnvAbove = findViewById<EditText>(R.id.etEnvAbove)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etDensity = findViewById<EditText>(R.id.etDensity)
        val btnCalc = findViewById<Button>(R.id.btnCalc)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvResultLabel = findViewById<TextView>(R.id.tvResultLabel)

        btnCalc.setOnClickListener {
            try {
                val envBelowStr = etEnvBelow.text.toString().trim()
                val envAboveStr = etEnvAbove.text.toString().trim()
                val heightStr = etHeight.text.toString().trim()
                val densityStr = etDensity.text.toString().trim()

                if (envBelowStr.isEmpty() || envAboveStr.isEmpty() || heightStr.isEmpty() || densityStr.isEmpty()) {
                    Toast.makeText(this, "لطفاً همهٔ فیلدها را پر کنید", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val envBelowCm = envBelowStr.toDouble()
                val envAboveCm = envAboveStr.toDouble()
                val heightM = heightStr.toDouble()
                val density = densityStr.toDouble()

                if (envBelowCm <= 0 || envAboveCm <= 0 || heightM <= 0 || density <= 0) {
                    Toast.makeText(this, "مقادیر باید بزرگتر از صفر باشند", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // تبدیل محیط از سانتی‌متر به متر
                val envBelowM = envBelowCm / 100.0
                val envAboveM = envAboveCm / 100.0

                // شعاع‌ها
                val R = envBelowM / (2.0 * PI)
                val r = envAboveM / (2.0 * PI)

                // حجم مخروط ناقص: V = (1/3) * π * h * (R^2 + R*r + r^2)
                val volume = (1.0 / 3.0) * PI * heightM * (R * R + R * r + r * r)

                // وزن
                val rawWeight = volume * density
                val weightRoundedUp = ceil(rawWeight).toInt()

                tvResultLabel.visibility = TextView.VISIBLE
                tvResult.visibility = TextView.VISIBLE
                tvResult.text = weightRoundedUp.toString()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "فرمت ورودی معتبر نیست", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "خطا: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
