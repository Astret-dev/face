package com.example.facedetection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.SurfaceView
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import com.example.facedetection.cosineSimilarity.isFaceMatching
import com.example.facedetection.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var Camera : Intent

    private lateinit var binding : ActivityMainBinding

    private val REQUEST_CAMERA = 10

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Camera = getSystemService(CAMERA_SERVICE) as CameraManager





    }*/

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture
    private lateinit var surfaceView: SurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){

//        this@MainActivity.surfaceView = binding.surfaceView
//        val captureBtn: Button = binding.captureBtn


                set1.setOnClickListener {
                    startCamera()
                }
            set2.setOnClickListener {
                startCamera2()

            }

            compareBtn.setOnClickListener {

                isFaceMatching((Img1.drawable as BitmapDrawable).bitmap,(Img2.drawable as BitmapDrawable).bitmap, context =this@MainActivity){
                    if(!it){
                        root.setBackgroundColor(getColor(R.color.red))
                    }
                    else{
                        root.setBackgroundColor(getColor(R.color.white))

                    }
                }
            }
        }




    }

   /* private fun isFaceMatching(selectedFace: Bitmap, capturedFace: Bitmap) {
        // Initialize the FaceRecognitionHelper
        val faceRecognitionHelper = FaceRecognitionHelper(this)

        // Get embeddings for the selected face
        faceRecognitionHelper.getEmbeddings(selectedFace) { selectedFaceEmbeddings ->
            Log.i("selected", "Selected face embeddings: $selectedFaceEmbeddings")
            val selected = selectedFaceEmbeddings

            // Get embeddings for the captured face
            faceRecognitionHelper.getEmbeddings(capturedFace) { capturedFaceEmbeddings ->
                Log.i("captured", "Captured face embeddings: $capturedFaceEmbeddings")
                val captured = capturedFaceEmbeddings
                // Calculate cosine similarity between the embeddings
                val cosineSimilarity =
                    calculateCosineSimilarity(selectedFaceEmbeddings, capturedFaceEmbeddings)
                Log.i("TAG", "Cosine similarity: $cosineSimilarity")

                // Check the result of face matching
                if (cosineSimilarity > 0.8) {
                    Toast.makeText(
                        this,
                        "Correct person$cosineSimilarity",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Wrong person detected$cosineSimilarity",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    // Method to calculate cosine similarity between two embeddings
    private fun calculateCosineSimilarity(
        embeddings1: FloatArray?,
        embeddings2: FloatArray?
    ): Float {
        val embed1 = embeddings1 == null
        val embed2 = embeddings2 == null
        Log.i("embed1", "calculateCosineSimilarity: $embed1")
        Log.i("embed2", "calculateCosineSimilarity: $embed2")
        if (embeddings2 == null) {
            return 0f
        }
        if (embeddings1!!.size > embeddings2.size) {
            return 0f // or handle the case appropriately
        }


        // Calculate dot product
        var dotProduct = 0f
        for (i in embeddings1.indices) {
            dotProduct += embeddings1[i] * embeddings2[i]
        }

        // Calculate magnitudes
        var magnitude1 = 0f
        var magnitude2 = 0f
        for (i in embeddings1.indices) {
            magnitude1 += embeddings1[i] * embeddings1[i]
            magnitude2 += embeddings2[i] * embeddings2[i]
        }
        magnitude1 = Math.sqrt(magnitude1.toDouble()).toFloat()
        magnitude2 = Math.sqrt(magnitude2.toDouble()).toFloat()
        Log.i("magnitude1", "calculateCosineSimilarity: $magnitude1")
        Log.i("magnitude2", "calculateCosineSimilarity: $magnitude2")


        // Calculate cosine similarity
        if (magnitude1 > 0 && magnitude2 > 0) {
            Log.i(
                "dotProductif",
                "calculateCosineSimilarity: " + dotProduct / (magnitude1 * magnitude2)
            )
            return dotProduct / (magnitude1 * magnitude2)
        }
        Log.i("dotProduct", "calculateCosineSimilarity: " + dotProduct / (magnitude1 * magnitude2))
        return dotProduct / (magnitude1 * magnitude2)
    }
*/


     val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data
            val bitmap : Bitmap? = data?.extras?.get("data") as Bitmap?

            with(binding){
                    Img1.setImageBitmap(bitmap)


            }
            // Process the captured image here
        }
    }


    private fun startCamera2() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher2.launch(cameraIntent)
    }
    val cameraLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data
            val bitmap : Bitmap? = data?.extras?.get("data") as Bitmap?

            with(binding){


                    Img2.setImageBitmap(bitmap)

            }
            // Process the captured image here
        }
    }


    private fun startCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }


    private fun getCapturedImage(imageUri: Uri?): Bitmap? {
        if (imageUri == null) return null  // Handle null URI

        return try {
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            bitmap
        } catch (e: Exception) {
            Log.e("ImageCapture", "Error loading captured image", e)
            null
        }
    }









}