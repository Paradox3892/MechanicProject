package com.example.navsample


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.navsample.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth
    lateinit var dialog: Dialog
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        sharedPref = getSharedPreferences("LoginInfo" , Context.MODE_PRIVATE)

        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)




        auth= FirebaseAuth.getInstance()
        databaseReference= FirebaseDatabase.getInstance().getReference("Users")
        mAuth = FirebaseAuth.getInstance()







        register.setOnClickListener{




            showProgressBar()
            val FirstName = binding.firstName.text.toString()
            val LastName = binding.lastName.text.toString()
            val UserName = binding.userName.text.toString()
            val Email = binding.emailAddress.text.toString()
            val Password = binding.userPassword.text.toString()

            val user=User(FirstName,LastName,UserName, Email,Password)

            if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                if (Password.length >= 8) {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                    mAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val uid= auth.currentUser?.uid

                                    if (uid != null) {
                                        val editor = sharedPref.edit()
                                        editor.clear()
                                        editor.apply()
                                        editor.putString("name" , binding.firstName.text.toString())
                                        editor.putString("username" , binding.userName.text.toString())
//                                        Toast.makeText(this , "Data Saved" , Toast.LENGTH_SHORT).show()
                                        editor.apply()



                                        databaseReference.child(uid).setValue(user).addOnCompleteListener{
                                            if(it.isSuccessful){
                                                val profleUpdate =
                                                    UserProfileChangeRequest.Builder()
                                                        .setDisplayName("$FirstName:$UserName")
                                                        .build()
                                                FirebaseAuth.getInstance().currentUser!!.updateProfile(profleUpdate)
                                                hideProgressbar()
                                                Toast.makeText(this@Register,"successfully Register",Toast.LENGTH_SHORT).show()

                                                intent.putExtra("name", FirstName)
                                                intent.putExtra("username", UserName)
//                                                intent.putExtra("email", Email)
                                                val i = Intent(this@Register, NavDrawerActivity::class.java)
                                                startActivity(i)
                                            } else{
                                                hideProgressbar()
                                                Toast.makeText(this@Register,"Failed to Register",Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                            }
                            else
                            {
                                hideProgressbar()
                                Toast.makeText(this@Register,"Account already exists on this Email",Toast.LENGTH_SHORT).show()

                            }

                        }
                } else  {
                    hideProgressbar()
                    userPassword.error = "Password must be atleast 8 character long"
                    userPassword.requestFocus()
                }
            } else {
                hideProgressbar()
                emailAddress.error = "Please enter a valid Email Address"
                emailAddress.requestFocus()
            }
        }

    }

    private  fun showProgressBar(){
        dialog=Dialog(this@Register)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    fun hideProgressbar(){
        dialog.dismiss()
    }
}


