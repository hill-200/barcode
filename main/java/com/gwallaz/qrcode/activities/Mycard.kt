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

class Mycard : AppCompatActivity() {

    private lateinit var viewModelDatabase: ViewModelDatabase
    private lateinit var backButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var createButton: Button
    private lateinit var cardName: EditText
    private lateinit var cardNumber: EditText
    private lateinit var cardImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycard)

        viewModelDatabase = ViewModelProvider(this)[ViewModelDatabase::class.java]
        backButton = findViewById(R.id.mycard_back)
        saveButton = findViewById(R.id.mycard_save)
        createButton = findViewById(R.id.mycard_create)

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

        cardNumber = findViewById(R.id.mycard_no)
        val text = cardNumber.text.toString().trim()
        val formatWriter = MultiFormatWriter()
        cardImageView = findViewById(R.id.mycard_image)

        try {

            val bitmatrix: BitMatrix = formatWriter.encode(text,BarcodeFormat.QR_CODE,400,400)
            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.createBitmap(bitmatrix)
            cardImageView.setImageBitmap(bitmap)

        }catch (e: WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToDatabase(){

        cardName = findViewById(R.id.mycard_name)
        val name = cardName.text.toString()
        val type = "Credit card"
        val image = R.drawable.baseline_credit_card
        val insert = TableCreate(0,name,type,image)
        viewModelDatabase.upsertRecord(insert)
    }
}