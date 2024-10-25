package com.example.pw3

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pw3.ui.theme.PW3Theme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    fun integral(f: (Double) -> Double, a: Double, b: Double, n: Int): Double {

                        val h = (b - a) / n
                        var sum = f(a) + f(b)

                        for (i in 1 until n) {
                            val x = a + i * h
                            sum += if (i % 2 == 0) 2 * f(x) else 4 * f(x)
                        }

                        return sum * (h / 3)
                    }
                    setContentView(R.layout.layout)


                    val resultField = findViewById<TextView>(R.id.result)
                    val PcInput = findViewById<TextView>(R.id.Pc)
                    val s1Input = findViewById<TextView>(R.id.s1)
                    val s2Input = findViewById<TextView>(R.id.s2)
                    val BInput = findViewById<TextView>(R.id.B)
                    val button = findViewById<TextView>(R.id.calculate)

                    button.setOnClickListener {
                        val Pc = PcInput.text.toString().toDoubleOrNull() ?: 0.0
                        val s1 = s1Input.text.toString().toDoubleOrNull() ?: 0.0
                        val s2 = s2Input.text.toString().toDoubleOrNull() ?: 0.0
                        val B = BInput.text.toString().toDoubleOrNull() ?: 0.0


                        val dW1 = integral({ p ->
                            (1 / (s1 * Math.sqrt(2 * Math.PI))) * Math.exp(
                                -Math.pow(
                                    p - Pc,
                                    2.0
                                ) / (2 * Math.pow(s1, 2.0))
                            )
                        }, 4.75, 5.25, 1000000)

                        val W1 = Pc * 24 * dW1

                        val Pryb1 = W1 * B

                        val W21 = Pc * 24 * (1 - dW1)

                        val Sch1 = W21 * B

                        val Riznytsia1 = Pryb1 - Sch1

                        val dW2 = integral({ p ->
                            (1 / (s2 * Math.sqrt(2 * Math.PI))) * Math.exp(
                                -Math.pow(
                                    p - Pc,
                                    2.0
                                ) / (2 * Math.pow(s2, 2.0))
                            )
                        }, 4.75, 5.25, 1000000)

                        val W12 = Pc * 24 * dW2

                        val Pryb2 = W12 * B

                        val W22 = Pc * 24 * (1 - dW2)

                        val Sch2 = W22 * B

                        val Riznytsia2 = Pryb2 - Sch2


                        resultField.text =
                            "Прибуток до покращення $Riznytsia1, після покращення - $Riznytsia2"
                    }
                }
            }
        }
    }
}
