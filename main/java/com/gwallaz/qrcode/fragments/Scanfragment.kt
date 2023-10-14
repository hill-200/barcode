package com.gwallaz.qrcode.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.gwallaz.qrcode.DisplayBarcode
import com.gwallaz.qrcode.QRImageAnalyzer
import com.gwallaz.qrcode.R
import com.gwallaz.qrcode.databinding.FragmentScanfragmentBinding
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class Scanfragment : Fragment() {
    private var _binding : FragmentScanfragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentScanfragmentBinding.inflate(inflater,container,false)

        codescanning()


        return binding.root
    }


    private fun startCamera(){
        val analyzer : QRImageAnalyzer
        val cameraExecutor :ExecutorService = Executors.newSingleThreadExecutor()

        val cameraProviderFuture = context?.let { ProcessCameraProvider.getInstance(it) }
        cameraProviderFuture?.addListener({

            //used to bind lifecycle of cameras to the lifecycle owner
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setImageQueueDepth(10)
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor,QRImageAnalyzer())




            //preview
                val preview = Preview.Builder()

                    .build()
                preview.setSurfaceProvider(binding.previewView.surfaceProvider)

            cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA,preview,imageAnalysis)

        }, activity?.let { ContextCompat.getMainExecutor(it.baseContext) })
    }


    private fun codescanning(){

        val activity = requireActivity()
        codeScanner = CodeScanner(requireContext(),binding.codeScannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback = DecodeCallback {
                activity.runOnUiThread{
                    Toast.makeText(activity,it.text,Toast.LENGTH_LONG).show()
                    

                }

                errorCallback = ErrorCallback {
                    activity.runOnUiThread {
                        Toast.makeText(activity,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            binding.codeScannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}