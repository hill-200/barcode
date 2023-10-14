package com.gwallaz.qrcode.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

class Sms : AppCompatActivity() {

    private  lateinit var viewModelDatabase: ViewModelDatabase
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var createButton: Button
    private lateinit var smsName: EditText
    private lateinit var smsSms: EditText
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        viewModelDatabase = ViewModelProvider(this)[ViewModelDatabase::class.java]
        saveButton = findViewById(R.id.sms_save)
        backButton = findViewById(R.id.sms_back)
        createButton = findViewById(R.id.sms_create)

        saveButton.setOnClickListener(View.OnClickListener {
            saveToDatabase()
        })

        backButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        })

        createButton.setOnClickListener(View.OnClickListener {
            createBarcode()
        })
    }

    private fun createBarcode(){
        smsSms = findViewById(R.id.sms_sms)
        val text = smsSms.text.toString().trim()
        val formatWriter = MultiFormatWriter()
        imageView = findViewById(R.id.sms_image)

        try {
            val bitmatrix: BitMatrix = formatWriter.encode(text,BarcodeFormat.QR_CODE,400,400)
            val encoder = BarcodeEncoder()
            val bitmap : Bitmap = encoder.createBitmap(bitmatrix)
            imageView.setImageBitmap(bitmap)

        }catch (e: WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToDatabase(){
        smsName = findViewById(R.id.sms_name)
        val name = smsName.text.toString()
        val type = "Sms"
        val image = R.drawable.baseline_sms
        val insert = TableCreate(0,name,type,image)
        viewModelDatabase.upsertRecord(insert)
    }

    @SuppressLint("MissingInflatedId")
    fun saveToMediaStorage(){
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this).inflate(R.layout.dialog_layout,null)
        val editText = inflater.findViewById<EditText>(R.id.dialog_edit_text)
        builder.setView(inflater)
        builder.setPositiveButton("Ok"){_,_ ->

        }
        builder.setNegativeButton("Cancel"){_,_ ->}
    }
}