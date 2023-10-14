package com.gwallaz.qrcode.activities

import android.annotation.SuppressLint
import android.content.Intent
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


class Website : AppCompatActivity() {


   private  lateinit var viewModelDatabase : ViewModelDatabase


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_website)
         viewModelDatabase = ViewModelProvider(this)[ViewModelDatabase::class.java]
        val websiteBackbutton : ImageButton = findViewById(R.id.website_back_button)

        val websiteCreateButton : Button = findViewById(R.id.website_create_button)

        websiteCreateButton.setOnClickListener {
            websiteBarcode()
        }

        websiteBackbutton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        })
        saveToDatabase()

    }
    private fun websiteBarcode(){

        val websiteEdittext : EditText = findViewById(R.id.website_edit_text)
        val websitetext = websiteEdittext.text.toString().trim()
        val formatWriter: MultiFormatWriter = MultiFormatWriter()
        val websiteImageview : ImageView = findViewById(R.id.website_image_view)

        try {
            val bitmatrix: BitMatrix = formatWriter.encode(websitetext,BarcodeFormat.QR_CODE,400,400)
            val encoder : BarcodeEncoder = BarcodeEncoder()
            val bitmap = encoder.createBitmap(bitmatrix)
            websiteImageview.setImageBitmap(bitmap)

        }catch (e : WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()

        }

    }

    private fun saveToDatabase(){

        val websiteSavebutton : Button = findViewById(R.id.website_save_button)
        websiteSavebutton.setOnClickListener(View.OnClickListener {

            val name = "Website"
            val type = "URL"
            val image = R.drawable.baseline_link
            val table = TableCreate(0,name, type, image)
            viewModelDatabase.upsertRecord(table)
            Toast.makeText(this,"Successfully added",Toast.LENGTH_SHORT).show()

        })
    }

}