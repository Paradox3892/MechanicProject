package com.example.navsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_trouble_shooting.*

class TroubleShooting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trouble_shooting)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}