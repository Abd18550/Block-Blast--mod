package com.example.blockblastassistant.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.blockblastassistant.R

class ActivityMainBinding private constructor(
    val root: View,
    val startOverlay: Button,
    val stopOverlay: Button,
    val sampleText: TextView
) {
    companion object {
        fun inflate(inflater: LayoutInflater): ActivityMainBinding {
            val root = inflater.inflate(R.layout.activity_main, null, false)
            return ActivityMainBinding(
                root,
                root.findViewById(R.id.startOverlay),
                root.findViewById(R.id.stopOverlay),
                root.findViewById(R.id.sampleText)
            )
        }
    }
}
