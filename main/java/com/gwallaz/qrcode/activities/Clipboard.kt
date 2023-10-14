package com.gwallaz.qrcode.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.gwallaz.qrcode.MainActivity
import com.gwallaz.qrcode.R
import com.gwallaz.qrcode.database.TableCreate
import com.gwallaz.qrcode.database.ViewModelDatabase
import com.journeyapps.barcodescanner.BarcodeEncoder

class Clipboard : FragmentActivity() {

      private lateinit var viewModel:ViewModelDatabase


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clipboard)
         val backButton : ImageButton = findViewById(R.id.go_back)
         val createButton : Button = findViewById(R.id.button_create)


        createButton.setOnClickListener(View.OnClickListener {
            createBarcode()
            insertRecord()
        })

        backButton.setOnClickListener(View.OnClickListener {


            startActivity(Intent(this,MainActivity::class.java))

        })

        viewModel = ViewModelProvider(this)[ViewModelDatabase::class.java]

    }


    private fun createBarcode(){
         val editText : EditText = findViewById(R.id.edit_text)
         val qrImage : ImageView = findViewById(R.id.qrcode_imageview)
        val text = editText.text.toString().trim()
        val formatWriter : MultiFormatWriter = MultiFormatWriter()

        try {
            val bitmatrix : BitMatrix = formatWriter.encode(text,BarcodeFormat.QR_CODE,400,400)
            val encoder : BarcodeEncoder = BarcodeEncoder()
            val bitmap : Bitmap = encoder.createBitmap(bitmatrix)
            qrImage.setImageBitmap(bitmap)

            //to hide the keyboard
            val manager : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(editText.applicationWindowToken)


        }catch (e : WriterException){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()

        }

    }

    private fun insertRecord(){

        val name = "Clipboard"
        val type = "Text"
        val image = R.drawable.baseline_clipboard
        val user = TableCreate(0,name, type, image)
        viewModel.upsertRecord(user)
        val editText: EditText = findViewById(R.id.edit_text)
        editText.setText("")
    }
}

private fun InputMethodManager.hideSoftInputFromWindow(applicationWindowToken: IBinder?) {

}


