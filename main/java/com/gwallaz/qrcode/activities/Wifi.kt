package com.gwallaz.qrcode.activities

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.gwallaz.qrcode.MainActivity
import com.gwallaz.qrcode.R
import com.gwallaz.qrcode.database.TableCreate
import com.gwallaz.qrcode.database.ViewModelDatabase
import com.journeyapps.barcodescanner.BarcodeEncoder

class Wifi : AppCompatActivity() {

    private  lateinit var viewModel: ViewModelDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        val wifiBackbutton : ImageButton = findViewById(R.id.wifi_back_button)
        val wifiCreatebutton : Button = findViewById(R.id.wifi_create_button)

        wifiBackbutton.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        })
        wifiCreatebutton.setOnClickListener(View.OnClickListener {
            wifiBarcode()
        })

        viewModel = ViewModelProvider(this)[ViewModelDatabase::class.java]
        saveToDatabase()
    }

    private fun wifiBarcode(){
        val wifiName : EditText = findViewById(R.id.wifi_edit_text_name)
        val wifiPassword : EditText = findViewById(R.id.wifi_edit_text_password)
        val wifiImageview : ImageView = findViewById(R.id.wifi_image_view)
        val formatWriter : MultiFormatWriter = MultiFormatWriter()
        val text = wifiName.text.toString().trim()
         val textPassword = wifiPassword.text.toString().trim()

        try {

           val bitMatrix : BitMatrix = formatWriter.encode(textPassword,BarcodeFormat.QR_CODE,400,400)
            val encoder : BarcodeEncoder = BarcodeEncoder()
            val bitmap : Bitmap = encoder.createBitmap(bitMatrix)
            wifiImageview.setImageBitmap(bitmap)

        }catch (e : WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }


    }

    private fun saveToDatabase(){
        val wifiSaveButton : Button = findViewById(R.id.wifi_save_button)
        wifiSaveButton.setOnClickListener(View.OnClickListener {
            val wifiName: EditText = findViewById(R.id.wifi_edit_text_name)
            val name = wifiName.text.toString()
            val type = "Wi-Fi"
            val image = R.drawable.wifi
            val insert = TableCreate(0, name, type, image)
            viewModel.upsertRecord(insert)
            Toast.makeText(this,"Saved successfully",Toast.LENGTH_SHORT).show()
        })

    }
}