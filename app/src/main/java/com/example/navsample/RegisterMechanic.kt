package com.example.navsample

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.example.navsample.databinding.ActivityRegisterMechanicBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register_mechanic.*


class RegisterMechanic : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private lateinit var imageUri: Uri

    lateinit var mAuth: FirebaseAuth
    private var imgFlag=0;

        private lateinit var binding: ActivityRegisterMechanicBinding
        private lateinit var auth: FirebaseAuth
        private lateinit var databaseReference: DatabaseReference
        private lateinit var storageReference: StorageReference
        private lateinit var dialog:Dialog
    private lateinit var sharedPref : SharedPreferences

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_register_mechanic)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            sharedPref = getSharedPreferences("LoginInfo" , Context.MODE_PRIVATE)

            binding= ActivityRegisterMechanicBinding.inflate(layoutInflater)
            setContentView(binding.root)
            auth= FirebaseAuth.getInstance()
            databaseReference= FirebaseDatabase.getInstance().getReference("Mechanic")
            mAuth = FirebaseAuth.getInstance()
            binding.register.setOnClickListener{
                showProgressBar()
                val mechanic_phNo=binding.mechanicPhNo.text.toString()
                val mechanic_name=binding.mechanicName.text.toString()
                val mechanic_prof=binding.mechanicProf.text.toString()
                val emailAddress=binding.mechanicEmailAddress.text.toString()
                val password=binding.mechanicPassword.text.toString()
                val username=binding.mechanicUsername.text.toString()

                val mechanic=Mechanic(mechanic_name,username,mechanic_phNo,mechanic_prof, emailAddress,password)


                if (Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    if (password.length >= 8) {
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                        mAuth.createUserWithEmailAndPassword(emailAddress, password,)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val uid= auth.currentUser?.uid

                                    if (uid != null) {
                                        databaseReference.child(uid).setValue(mechanic).addOnCompleteListener{
                                            if(it.isSuccessful){
                                                val editor = sharedPref.edit()
                                                editor.clear()
                                                editor.apply()
                                                editor.putString("name" , binding.mechanicName.text.toString())
                                                editor.putString("username" , binding.mechanicUsername.text.toString())
//                                        Toast.makeText(this , "Data Saved" , Toast.LENGTH_SHORT).show()
                                                editor.apply()





                                                val profleUpdate =
                                                    UserProfileChangeRequest.Builder()
                                                        .setDisplayName("$mechanic_name:$username")
                                                        .build()
                                                FirebaseAuth.getInstance().currentUser!!.updateProfile(profleUpdate)

                                                hideProgressbar()
                                                Toast.makeText(this@RegisterMechanic,"Registered Successfully",Toast.LENGTH_SHORT).show()

                                                intent.putExtra("name", mechanic_name)
                                                intent.putExtra("username", username)
//                                                intent.putExtra("email", emailAddress)
                                                val i = Intent(this@RegisterMechanic, NavDrawerActivity::class.java)
                                                startActivity(i)
                                            } else{
                                                hideProgressbar()
                                                Toast.makeText(this@RegisterMechanic,"Failed to Register",Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    hideProgressbar()
                                    Toast.makeText(this@RegisterMechanic,"Account already exists on this Email",Toast.LENGTH_SHORT).show()

                                }

                            }
                    } else  {
                        hideProgressbar()
                        mechanic_Password.setError("Password must be atleast 8 character long")
                        mechanic_Password.requestFocus()
                    }
                } else {
                    hideProgressbar()
                    mechanic_emailAddress.setError("Please enter a valid Email Address")
                    mechanic_emailAddress.requestFocus()
                }
            }

//            imageView = findViewById(R.id.profile_image)
//            button = findViewById(R.id.img_btn)
//            button.setOnClickListener {
//                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//                startActivityForResult(gallery, pickImage)
//            }
            }

    private fun uploadProfilePic() {
        if(imgFlag==0)
        {
            imageUri=Uri.parse("android.resource://$packageName/${R.drawable.man}")
        }
        storageReference=FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid+".jpg")
        storageReference.putFile(imageUri).addOnSuccessListener {
            hideProgressbar()

        }.addOnFailureListener{
            hideProgressbar()
            Toast.makeText(this@RegisterMechanic,"Profile pic upload failed",Toast.LENGTH_SHORT).show()
        }



    }

    private fun showProgressBar(){
        dialog=Dialog(this@RegisterMechanic)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressbar(){
        dialog.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (resultCode == RESULT_OK && requestCode == pickImage) {
                    imageUri = data?.data!!
                    imgFlag=1
                    imageView.setImageURI(imageUri)
                }
            }
        }