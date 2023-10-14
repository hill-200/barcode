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

class Telephone : AppCompatActivity() {

    private lateinit var viewModelDatabase: ViewModelDatabase
    private lateinit var backButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var createButton: Button
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telephone)

        viewModelDatabase = ViewModelProvider(this)[ViewModelDatabase::class.java]
        backButton = findViewById(R.id.telephone_back)
        saveButton = findViewById(R.id.telephone_save)
        createButton = findViewById(R.id.telephone_create)

        backButton.setOnClickListener(View.OnClickListener {
           startActivity(Intent(this,MainActivity::class.java))
        })

        saveButton.setOnClickListener(View.OnClickListener {
            saveToDatabase()
        })

        createButton.setOnClickListener(View.OnClickListener {
           createBarcode()
        })
    }

    private fun createBarcode(){
        imageView = findViewById(R.id.telephone_image)
        editText = findViewById(R.id.telephone_contact)
        val text = editText.text.toString().trim()
        val formatWriter = MultiFormatWriter()

        try {
            val bitMatrix:BitMatrix = formatWriter.encode(text,BarcodeFormat.QR_CODE,400,400)
            val encoder = BarcodeEncoder()
            val bitmap : Bitmap = encoder.createBitmap(bitMatrix)
            imageView.setImageBitmap(bitmap)

        }catch (e: WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToDatabase(){

        editText = findViewById(R.id.telephone_contact)
        val name = editText.text.toString()
        val type = "Telephone"
        val image = R.drawable.baseline_phone
        val insert = TableCreate(0,name,type,image)
        viewModelDatabase.upsertRecord(insert)
    }
}