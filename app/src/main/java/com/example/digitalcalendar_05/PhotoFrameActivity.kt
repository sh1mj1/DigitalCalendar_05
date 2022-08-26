package com.example.digitalcalendar_05

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.timer

class PhotoFrameActivity: AppCompatActivity() {

    // Mark -Properties/////////////////////////////////////////
    private val photoList = mutableListOf<Uri>()
    private var currentPosition = 0

    private val photoImageView: ImageView by lazy { findViewById(R.id.photo_Iv) }
    private val backgroundPhotoImageView: ImageView by lazy { findViewById(R.id.backgroundPhoto_Iv) }


    // Mark -Lifecycle/////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("PhotoFrameActivity", "onCreate()")
        setContentView(R.layout.activity_photoframe)

        getPhotoUriFromIntent()
        startTimer()
    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer(){
        timer(period = 5 * 1000) {
            runOnUiThread {
                val current = currentPosition
                val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

                backgroundPhotoImageView.setImageURI(photoList[current])

                photoImageView.alpha = 0f // alpha 는 투명도임.
                photoImageView.setImageURI(photoList[next])

                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            }
        }
    }



}