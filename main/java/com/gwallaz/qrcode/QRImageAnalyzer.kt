package com.gwallaz.qrcode

import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


 class QRImageAnalyzer : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()




    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image
        if (mediaImage != null) {

            val img = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient(options)
            scanner.process(img)
                .addOnSuccessListener { barcodes ->
                     kotlin.run{
                        for(barcode in barcodes){



                            when (barcode.valueType) {
                                Barcode.TYPE_WIFI -> {
                                    val rawValue: String? = barcode.rawValue
                                    val ssid = barcode.wifi!!.ssid
                                    val password = barcode.wifi!!.password
                                    val type = barcode.wifi!!.encryptionType


                                      }
                                }


                            }
                        }


                    }



                    image.close()

                } else {
                    image.close()
        }


        }

    }

