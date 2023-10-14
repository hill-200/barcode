package com.gwallaz.qrcode.activities

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class Contact : AppCompatActivity() {

    private lateinit var viewModelDatabase: ViewModelDatabase
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var createButton: Button
    private lateinit var contactName: EditText
    private lateinit var contactNumber: EditText
    private lateinit var contactImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        viewModelDatabase = ViewModelProvider(this)[ViewModelDatabase::class.java]
        saveButton = findViewById(R.id.contact_save)
        backButton = findViewById(R.id.contact_back)
        createButton = findViewById(R.id.contact_create)

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
        contactNumber = findViewById(R.id.contact_no)
        contactImage = findViewById(R.id.contact_image)
        val formatWriter = MultiFormatWriter()
        val number = contactNumber.text.toString().trim()

        try {
            val bitmatrix: BitMatrix = formatWriter.encode(number,BarcodeFormat.QR_CODE,400,400)
            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.createBitmap(bitmatrix)
            contactImage.setImageBitmap(bitmap)


        }catch (e : WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }

    }

    private fun saveToDatabase(){

        contactName = findViewById(R.id.contact_name)
        contactNumber = findViewById(R.id.contact_no)
        val name = contactName.text.toString()
        val type = "Contact"
        val image = R.drawable.baseline_contact
        val table = TableCreate(0,name,type,image)
        viewModelDatabase.upsertRecord(table)
        contactNumber.setText("")
        contactName.setText("")
    }

    private fun saveToStorage(bitmap:Bitmap){
        val imageName = "${System.currentTimeMillis()}.jpg"
        var fileOutputStream: OutputStream? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME,imageName)
                    put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES)
                    }
                val imageUri : Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
                fileOutputStream = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }
        }else{
            val imagesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDirectory,imageName)
            fileOutputStream = FileOutputStream(image)
        }
        fileOutputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,it)
            Toast.makeText(this,"Successfully captures the view",Toast.LENGTH_LONG).show()
        }
    }
}