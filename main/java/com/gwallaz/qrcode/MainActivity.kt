package com.gwallaz.qrcode

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gwallaz.qrcode.create.CreateFragment

import com.gwallaz.qrcode.history.Historyfragment
import com.gwallaz.qrcode.fragments.Scanfragment
import com.gwallaz.qrcode.fragments.Settingsfragment

class MainActivity : AppCompatActivity() {

    private val code = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissions()
        val scanfragment = Scanfragment()
        val createfragment = CreateFragment()
        val historyfragment = Historyfragment()


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        setCurrentFragment(createfragment)
            bottomNavigationView.setOnNavigationItemSelectedListener {
                when(it.itemId){

                    R.id.create -> setCurrentFragment(createfragment)
                    R.id.scan -> setCurrentFragment(scanfragment)
                    R.id.history -> setCurrentFragment(historyfragment)

                }
                true
            }

    }


        private fun setCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, fragment)
                commit()
            }

    private fun permissions() {

        if(ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(Manifest.permission.CAMERA),code)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == code){

            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this@MainActivity,"Permission required for scanning",Toast.LENGTH_SHORT).show()
            }
        }
    }



}