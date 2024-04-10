package com.example.app06042

import java.net.HttpURLConnection
import java.net.URL

public class ForecastAPI {
    val baseURl = "https://api.open-meteo.com/v1/forecast?"

    public fun getWeather(
        lat: Double,
        lon: Double,
        temp: Boolean,
        wind: Boolean,
        humidity: Boolean,
        apparent: Boolean
    ): String {

        var current = "current="
        if (temp)
            current += "temperature_2m,"
        if (wind)
            current += "wind_speed_10m,"
        if (humidity)
            current += "relative_humidity_2m,"
        if (apparent)
            current += "apparent_temperature,"
        current = current.substring(0, current.length - 1)

        val url = "${baseURl}latitude=${lat}&longitude=${lon}&${current}&wind_speed_unit=ms"
        return getURLDate(url)
    }

    public fun getURLDate(path: String): String {
        val connection = URL(path).openConnection() as HttpURLConnection
        val data = connection.inputStream.bufferedReader().readText()
        return data
    }
}