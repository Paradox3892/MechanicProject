package com.example.navsample


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.navsample.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dialog:Dialog
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref : SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
//        val actionBar=supportActionBar
//        actionBar!!.title="Account";
        sharedPref = getSharedPreferences("LoginInfo" , Context.MODE_PRIVATE)


        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()


        binding.client.setOnClickListener {
            showProgressBar()
            val Email = binding.email.text.toString()
            val Password = binding.password.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(Email).matches() && Password.length >= 8) {
                mAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            hideProgressbar()
                            val user = mAuth.currentUser
                            val str = user!!.displayName
                            val delim = ":"

                            val list = str?.split(delim)

                            val nameUser= list?.get(0).toString()
                            val UserNameUser= list?.get(1).toString()
                            val editor = sharedPref.edit()
                            editor.clear()
                            editor.apply()
                            editor.putString("name" , nameUser)
                            editor.putString("username" , UserNameUser)
//                                        Toast.makeText(this , "Data Saved" , Toast.LENGTH_SHORT).show()
                            editor.apply()
                            if(user!=null) {
                                hideProgressbar()
                                val i = Intent(this@Login, NavDrawerActivity::class.java)
//                                intent.putExtra("name", name)
//                                intent.putExtra("username", username)
//                                intent.putExtra("email", Email)
                                startActivity(i)
//
                            }
                            else {
                                hideProgressbar()
                                Toast.makeText(this@Login, "Login logiun Failed", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            hideProgressbar()
                            Toast.makeText(this@Login, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {

                hideProgressbar()
                email.error = "Enter a valid Email address"
                email.requestFocus()
            } else {
                hideProgressbar()
                password.error = "Password must be atleast 8 character long"
                password.requestFocus()
            }
        }

        mechanic.setOnClickListener {
            showProgressBar()
            val Email = email.text.toString()
            val Password = password.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(Email).matches() && Password.length >= 8) {
                mAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            hideProgressbar()
                            val user = mAuth.currentUser
                            if(user!=null) {


                                    hideProgressbar()
                                    val i = Intent(this@Login, NavDrawerActivity::class.java)
//                                    intent.putExtra("name", name)
//                                    intent.putExtra("username", username)
//                                    intent.putExtra("email", Email)
                                    startActivity(i)
//
                            }
                        } else {
                            hideProgressbar()
                            Toast.makeText(this@Login, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {

                hideProgressbar()
                email.error = "Enter a valid Email address"
                email.requestFocus()
            } else {
                hideProgressbar()
                password.error = "Password must be atleast 8 character long"
                password.requestFocus()
            }
        }

        clientAcc.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        signupEmail.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        signupFB.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        mechanicAcc.setOnClickListener{
            val intent = Intent(this, RegisterMechanic::class.java)
            startActivity(intent)
        }



    }

    private fun showProgressBar(){
        dialog= Dialog(this@Login)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressbar(){
        dialog.dismiss()
    }
}


