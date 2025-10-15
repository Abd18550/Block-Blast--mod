package com.example.blockblastassistant.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.blockblastassistant.databinding.ActivityMainBinding
import com.example.blockblastassistant.overlay.OverlayService

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val capturePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            OverlayService.start(this, result.resultCode, result.data!!)
            Toast.makeText(this, "Overlay started", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Screen capture permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startOverlay.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                )
                startActivity(intent)
                return@setOnClickListener
            }
            val mpm = getSystemService(MediaProjectionManager::class.java)
            val intent = mpm.createScreenCaptureIntent()
            capturePermissionLauncher.launch(intent)
        }

        binding.stopOverlay.setOnClickListener {
            OverlayService.stop(this)
        }

        binding.sampleText.setTextColor(Color.WHITE)
    }
}
