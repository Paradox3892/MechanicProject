package com.example.navsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_feedback.*

class Feedback : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        home_btn.setOnClickListener{
            val intent = Intent(this, NavDrawerActivity::class.java)
            startActivity(intent)
        }
        check.setOnClickListener{
            feedback_txt.text = "Thanks for the feedback"

        }


    }
}