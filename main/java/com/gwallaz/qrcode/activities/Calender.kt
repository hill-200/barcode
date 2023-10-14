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

class Calender : AppCompatActivity() {

    private lateinit var viewModelDatabase: ViewModelDatabase
    private lateinit var backButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var createButton: Button
    private lateinit var calenderEvent: EditText
    private lateinit var calenderDate: EditText
    private lateinit var calenderImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        viewModelDatabase = ViewModelProvider(this)[ViewModelDatabase::class.java]
        backButton = findViewById(R.id.calender_back)
        saveButton = findViewById(R.id.calender_save)
        createButton = findViewById(R.id.calender_create)

        backButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        })

        saveButton.setOnClickListener(View.OnClickListener {
            saveToDatabase()
        })

        createButton.setOnClickListener(View.OnClickListener {
            createBarCode()
        })
    }

    private fun createBarCode(){

        calenderDate = findViewById(R.id.calender_date)
        calenderImage = findViewById(R.id.calender_image)
        val text = calenderDate.text.toString().trim()
        val formatWriter = MultiFormatWriter()

        try {

            val bitmatrix: BitMatrix = formatWriter.encode(text,BarcodeFormat.QR_CODE,400,400)
            val encoder = BarcodeEncoder()
            val bitmap : Bitmap = encoder.createBitmap(bitmatrix)
            calenderImage.setImageBitmap(bitmap)

        }catch (e: WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToDatabase(){

        calenderEvent = findViewById(R.id.calender_event)
        calenderDate = findViewById(R.id.calender_date)
        val name = calenderEvent.text.toString().trim()
        val type = "Calender"
        val image = R.drawable.baseline_calendar_month
        val insert = TableCreate(0,name,type, image)
        viewModelDatabase.upsertRecord(insert)
        calenderEvent.setText("")
        calenderDate.setText("")
    }
}