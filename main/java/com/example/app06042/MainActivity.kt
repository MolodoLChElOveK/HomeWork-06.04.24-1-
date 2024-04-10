package com.example.app06042

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edCoordsLat = findViewById<EditText>(R.id.edCoordsLat)
        val edCoordsLon = findViewById<EditText>(R.id.edCoordsLon)
        val tAnswer = findViewById<TextView>(R.id.tAnswer)
        val chTemperature = findViewById<CheckBox>(R.id.chTemperature)
        val chWindSpeed = findViewById<CheckBox>(R.id.chWindSpeed)
        val chHumidity = findViewById<CheckBox>(R.id.chHumidity)
        val chApparentTemperature = findViewById<CheckBox>(R.id.chApparentTemperature)
        val btnGetWeather = findViewById<Button>(R.id.btnGetWeather)
        btnGetWeather.setOnClickListener {
            val api = ForecastAPI()

            thread {
                val text = api.getWeather(
                    edCoordsLat.text.toString().trim().toDouble(), edCoordsLon.text.toString().trim().toDouble(),
                    chTemperature.isChecked, chWindSpeed.isChecked, chHumidity.isChecked, chApparentTemperature.isChecked
                )
                var temp = 0.0
                var windSpeed = 0.0
                var humidity = 0.0
                var apparent_temperature = 0.0
                var parsedText = ""
                val json = JSONObject(text)

                if (json.has("current")){
                    val current = json.getJSONObject("current")

                    if (current.has("temperature_2m")){
                        temp = current.getDouble("temperature_2m")
                        parsedText += "Температура(°C): $temp\n"
                    }
                    if (current.has("apparent_temperature")){
                        apparent_temperature = current.getDouble("apparent_temperature")
                        parsedText += "Температура по ощущениям(°C): $apparent_temperature\n"
                    }
                    if (current.has("wind_speed_10m")){
                        windSpeed = current.getDouble("wind_speed_10m")
                        parsedText += "Скорость ветра(м/с): $windSpeed\n"
                    }
                    if (current.has("relative_humidity_2m")){
                        humidity = current.getDouble("relative_humidity_2m")
                        parsedText += "Влажность(%): $humidity\n"
                    }

                }

                runOnUiThread {
                    tAnswer.setText(parsedText)
                }
            }
        }

    }
}