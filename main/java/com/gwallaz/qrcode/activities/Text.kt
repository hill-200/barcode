package com.gwallaz.qrcode.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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

class Text : AppCompatActivity() {

    private lateinit var viewModel: ViewModelDatabase
    private lateinit var saveButton: Button
    private lateinit var createButton: Button
    private lateinit var backButton: Button
    private lateinit var editText: EditText
    private lateinit var imageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        viewModel = ViewModelProvider(this)[ViewModelDatabase::class.java]
        createButton = findViewById(R.id.text_create_buttons)
        backButton = findViewById(R.id.text_back_button)
        saveButton = findViewById(R.id.text_save_button)

        saveButton.setOnClickListener(View.OnClickListener {
            saveToDatabase()
        })

        createButton.setOnClickListener(View.OnClickListener {
            createBarcode()
        })

        backButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        })
    }

    private fun createBarcode(){
        editText = findViewById(R.id.edit_text_text)
        imageView = findViewById(R.id.text_Imageview)
        val formatWriter = MultiFormatWriter()
        val text = editText.text.toString().trim()

        try {


            val bitMatrix: BitMatrix = formatWriter.encode(text,BarcodeFormat.QR_CODE,400,400)
            val encoder = BarcodeEncoder()
            val bitmap : Bitmap = encoder.createBitmap(bitMatrix)
            imageView.setImageBitmap(bitmap)

        }catch (e : WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }

    }

    private fun saveToDatabase(){
       val image = R.drawable.baseline_text
       val name = "Text"
       val type = "text"
       val upsert = TableCreate(0,name,type,image)
       viewModel.upsertRecord(upsert)

    }
}