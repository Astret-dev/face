package com.example.facedetection

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.example.facedetection.FaceRecognitionHelper.EmbeddingsCallback

object cosineSimilarity  {

    fun isFaceMatching(selectedFace: Bitmap, capturedFace: Bitmap, context: Context, selectedLandmark: (Bitmap) -> Unit, capturedLandmark: (Bitmap) -> Unit) {



        // Initialize the FaceRecognitionHelper
        val faceRecognitionHelper = FaceRecognitionHelper(context)

// Get embeddings for the selected face
        faceRecognitionHelper.getEmbeddings(selectedFace,
            { selectedFaceEmbeddings ->
                Log.i("selected", "Selected face embeddings: $selectedFaceEmbeddings")
                val selected = selectedFaceEmbeddings

                // Get embeddings for the captured face
                faceRecognitionHelper.getEmbeddings(capturedFace,
                    { capturedFaceEmbeddings ->
                        Log.i("captured", "Captured face embeddings: $capturedFaceEmbeddings")
                        val captured = capturedFaceEmbeddings
                        // Calculate cosine similarity between the embeddings
                        val cosineSimilarity =
                            calculateCosineSimilarity(selectedFaceEmbeddings, capturedFaceEmbeddings)
                        Log.i("TAG", "Cosine similarity: $cosineSimilarity")

                        // Add your logic here based on the cosine similarity

                        if (cosineSimilarity > 0.8) {




                            Toast.makeText(
                                context,
                                "Correct person$cosineSimilarity",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Wrong person detected$cosineSimilarity",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    { landmarkedSelectedImage ->

                        selectedLandmark(landmarkedSelectedImage)
                        // Implement your logic to handle the landmarked image if necessary
                        // This lambda will be invoked when the landmarked image is ready
                    }
                )
            },
            { landmarkedCapturedImage ->

                capturedLandmark(landmarkedCapturedImage)
                // Implement your logic to handle the landmarked image if necessary
                // This lambda will be invoked when the landmarked image is ready

            }
        )


        // Check the result of face matching

            }
        }




    // Method to calculate cosine similarity between two embeddings
    fun calculateCosineSimilarity(
        embeddings1: FloatArray?,
        embeddings2: FloatArray?
    ): Float {
//        Log.i("embed1", "calculateCosineSimilarity: $embed1")
//        Log.i("embed2", "calculateCosineSimilarity: $embed2")

        if (embeddings1== null || embeddings2 == null) {
            return 0f
        }
        if (embeddings1.size > embeddings2.size) {
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
//        Log.i("magnitude1", "calculateCosineSimilarity: $magnitude1")
//        Log.i("magnitude2", "calculateCosineSimilarity: $magnitude2")


        // Calculate cosine similarity
        if (magnitude1 > 0 && magnitude2 > 0) {
//            Log.i(
//                "dotProductif",
//                "calculateCosineSimilarity: " + dotProduct / (magnitude1 * magnitude2)
//            )
            return dotProduct / (magnitude1 * magnitude2)
        }
//        Log.i("dotProduct", "calculateCosineSimilarity: " + dotProduct / (magnitude1 * magnitude2))
        return dotProduct / (magnitude1 * magnitude2)
    }

