package com.example.multilenguaje

import android.content.Intent
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var btnOpenCamera: Button
    private lateinit var btnOpenVideo: Button
    private lateinit var map : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOpenCamera = findViewById(R.id.btnOpenCamera)
        btnOpenVideo = findViewById(R.id.btnOpenVideo)

        btnOpenCamera.setOnClickListener {
            takeAPicture()
        }
        btnOpenVideo.setOnClickListener {
            takeAVideo()
        }

        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = packageName
        osmConfig.osmdroidBasePath = cacheDir
        osmConfig.osmdroidTileCache = File(cacheDir, "titles")

        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)

        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        locationOverlay.enableMyLocation()
        map.overlays.add(locationOverlay)

        locationOverlay.runOnFirstFix {
            val currentGeoPoint = locationOverlay.myLocation
            runOnUiThread {
                map.controller.animateTo(currentGeoPoint)
                map.controller.setZoom(15.0)
            }
        }
    }

    fun takeAPicture(){
        val IMAGE_CAPTURE_CODE=1001

        val cameraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE)
    }

    fun takeAVideo(){
        val IMAGE_CAPTURE_CODE=1001

        val cameraIntent= Intent(MediaStore.ACTION_VIDEO_CAPTURE)

        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            Toast.makeText(this,"Se tomó foto/video",Toast.LENGTH_SHORT).show()
        }
    }
}
