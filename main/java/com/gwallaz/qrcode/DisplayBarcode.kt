package com.gwallaz.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class DisplayBarcode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_barcode)
        initViews()
    }

    private fun initViews() {
        //Initialize the views
        val backbutton = findViewById<ImageButton>(R.id.backbutton)
        val wifi = findViewById<ImageView>(R.id.wifi)
        val textWifi = findViewById<TextView>(R.id.text_wifi)
        val network = findViewById<TextView>(R.id.network)
        val actualNetwork = findViewById<TextView>(R.id.actual_network)
        val password = findViewById<TextView>(R.id.password)
        val actualPassword = findViewById<TextView>(R.id.actual_password)
        val securityType = findViewById<TextView>(R.id.security_type)
        val actualSecurityType = findViewById<TextView>(R.id.actual_security_type)

 //Getting the extras
        val values = intent.extras
        val passwd = intent.getStringExtra("password")
        val security = intent.getStringExtra("security")
        val name = intent.getStringExtra("network")

        actualNetwork.text = name
        actualPassword.text = passwd
        actualSecurityType.text = security
        textWifi.text = "Wi-Fi"
        wifi.setImageResource(R.drawable.wifi)
        backbutton.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this,MainActivity::class.java))

        })


    }
}