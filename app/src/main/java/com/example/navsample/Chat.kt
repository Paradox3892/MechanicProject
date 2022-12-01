package com.example.navsample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_chat.*

class Chat : AppCompatActivity() {
    private lateinit var phoneNo:String
    private lateinit var name:String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        phoneNo = intent.getStringExtra("phNo").toString()
        name = intent.getStringExtra("mechanic").toString()

        prof_txt3.setText(name)
        prof_txt33.setText(name)
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED

        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.RECEIVE_SMS,android.Manifest.permission.SEND_SMS),111)
        receiveMsg()
        feedback.setOnClickListener{
            val intent = Intent(this, Feedback::class.java)
            startActivity(intent)
        }
        findViewById<TextView>(R.id.phoneNo_txt).text = phoneNo
        findViewById<LinearLayout>(R.id.phoneNo_ll).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:03188896883")
            intent.data = Uri.parse("tel:$phoneNo")
            startActivity(intent)
        }
        findViewById<Button>(R.id.dial2).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:03188896883")
            intent.data = Uri.parse("tel:$phoneNo")
            startActivity(intent)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            receiveMsg()
            val btnSend = findViewById<Button>(R.id.dial2)
            if(msg.text==null) {
                msg.setText("null message")
            }
            var etMsg =" "
            etMsg= findViewById<EditText>(R.id.msg).toString()
            btnSend.setOnClickListener {
                val sms = SmsManager.getDefault()
                sms.sendTextMessage(phoneNo, null, etMsg, null, null)
                Toast.makeText(this, "SMS sent.", Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(this," Permission denied, please try again.", Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun receiveMsg() {
        val br = object: BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                for(sms in  Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                    //   val etPhoneNo= findViewById<EditText>(R.id.etPhoneNo)
//                    etPhoneNo.setText(sms.originatingAddress)
//                    // val etMsg= findViewById<EditText>(R.id.etMsg)
                    msg.setText(sms.displayMessageBody)
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}