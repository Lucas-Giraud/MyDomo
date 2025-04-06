package com.example.mydomo.page

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mydomo.Api
import com.example.mydomo.R

class OpeningPage : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var houseID : String
    private lateinit var deviceID : String
    private lateinit var deviceName : String

    private lateinit var progressBar: ProgressBar
    private var progress : Float = 0.0f
    private val handler = Handler(Looper.getMainLooper()) // Valeur entre 0.0 et 1.0
    private var isRunning = false
    private var direction = 0  // 1 = ouverture, -1 = fermeture
    private val interval = 50L // chaque tick = 50ms
    private val duration = 7000L // durée totale pour ouverture/fermeture
    private val totalSteps = duration / interval
    private val stepSize = 1.0f / totalSteps // ≈ 0.00714

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                progress += stepSize  * direction // Vitesse de déplacement
                progress = progress.coerceIn(0.0f, 1.0f)
                progressBar.progress = (progress * 100).toInt()

                if (progress == 0.0f || progress == 1.0f) {
                    isRunning = false
                } else {
                    handler.postDelayed(this, interval)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opening_page)

        token = intent.getStringExtra("token").toString()
        houseID = intent.getStringExtra("houseID").toString()
        deviceID = intent.getStringExtra("deviceID").toString()
        deviceName = intent.getStringExtra("deviceName").toString()
        progress = intent.getFloatExtra("progress", 0.0f)
        findViewById<TextView>(R.id.lblDevice).text = deviceName

        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = (progress * 100).toInt()
    }

    fun Open(view: View){
        val command = "OPEN"
        val data = mapOf("command" to command)
        Api().post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices/$deviceID/command", data, ::openSuccess, token)
    }

    fun Stop(view: View){
        val command = "STOP"
        val data = mapOf("command" to command)
        Api().post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices/$deviceID/command", data, ::stopSuccess, token)
    }

    fun Close(view: View){
        val command = "CLOSE"
        val data = mapOf("command" to command)
        Api().post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseID/devices/$deviceID/command", data, ::closeSuccess, token)
    }

    fun openSuccess(responseCode : Int){
        if(responseCode == 200) {
            if (!isRunning) {
                direction = 1
                isRunning = true
                handler.post(updateRunnable)
            }
        }
    }

    fun stopSuccess(responseCode : Int){
        if(responseCode == 200) {
            isRunning = false
            handler.removeCallbacks(updateRunnable)
        }
    }

    fun closeSuccess(responseCode : Int){
        if(responseCode == 200) {
            if (!isRunning) {
                direction = -1
                isRunning = true
                handler.post(updateRunnable)
            }
        }
    }

    fun goBackDevice(view: View){
        finish()
    }
}