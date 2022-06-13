package com.example.myweather

import android.app.DownloadManager
import android.app.DownloadManager.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweather.moduls.weather
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    val apiKey = "80ca18657b8a4a8726ebc44224105308"
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.searchButton)
        button.setOnClickListener {
            getWeather()
            findViewById<EditText>(R.id.searchInput).text.clear()

        }
    }

    private fun getWeather() {
        val searchInput = findViewById<EditText>(R.id.searchInput)
        try {

            if (searchInput.text.isNotEmpty()) {
                var fullURL =
                    "http://api.openweathermap.org/data/2.5/weather?q=${searchInput.text}&units=metric&appid=$apiKey"
                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(Request.Method.POST, fullURL,
                    { response ->
                        var results = gson.fromJson(response, weather::class.java)

                        findViewById<TextView>(R.id.temp).text =
                            results.main.temp.toInt().toString() + "C"
                        findViewById<TextView>(R.id.status).text = results.weather[0].main
                        findViewById<TextView>(R.id.address).text =
                            results.name + ", " + results.sys.country
                        findViewById<TextView>(R.id.temp_min).text =
                            "Min temp:, " + results.main.temp_min + "C"
                        findViewById<TextView>(R.id.temp_max).text =
                            "Max temp:, " + results.main.temp_max + "C"


                    }, {
                        Toast.makeText(this, "Something went worng!", Toast.LENGTH_SHORT).show()
                        println(it.message)
                    })

                queue.add(stringRequest)
            }else {

                Toast.makeText(this, "Type Something!", Toast.LENGTH_SHORT).show()

            }
        }catch (err:Exception) {
        }

    }
}