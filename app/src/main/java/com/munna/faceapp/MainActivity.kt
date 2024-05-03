package com.munna.faceapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.munna.faceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if(intent.resolveActivity(packageManager)!=null){
                startActivityForResult(intent,100)
            }else{
                Toast.makeText(this, "Camera Error", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && resultCode == RESULT_OK){
            val extras = data?.extras
            val bitmap = extras?.get("data") as? Bitmap
            if(bitmap != null){
                detectFace(bitmap)
            }
        }
    }

    private fun detectFace(bitmap: Bitmap) {

        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(options)
        val image = InputImage.fromBitmap(bitmap,0)


        val result = detector.process(image)
            .addOnSuccessListener {faces->
                var adapter = ImageAdapter(this,faces)
                binding.rvFaces.adapter = adapter
                binding.rvFaces.layoutManager = LinearLayoutManager(this)
                binding.btn.text = faces.size.toString()
            }
            .addOnFailureListener{e->
                binding.btn.text = e.toString()
            }

    }
}