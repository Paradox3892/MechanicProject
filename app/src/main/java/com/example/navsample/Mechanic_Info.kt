package com.example.navsample

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.navsample.databinding.ActivityMechanicInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_mechanic_info.*
import java.io.File


class Mechanic_Info : AppCompatActivity() {
    private lateinit var binding: ActivityMechanicInfoBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private lateinit var mechanic: Mechanic
    private lateinit var uid:String
    private lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mechanic_info)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        name = intent.getStringExtra("name").toString()
    binding=ActivityMechanicInfoBinding.inflate(layoutInflater)
    setContentView(binding.root)
    auth= FirebaseAuth.getInstance()
    uid=auth.currentUser?.uid.toString()

    databaseReference=FirebaseDatabase.getInstance().getReference("Mechanic/")
    if(uid.isNotEmpty())
    {
        getUserData()
    }

        message.setOnClickListener{
            val intent = Intent(this, Chat::class.java)
            intent.putExtra("phNo", mechanic.phoneNo)
            intent.putExtra("mechanic", mechanic.name)
            startActivity(intent)

        }
    }

    private fun getUserData() {
        showProgressBar()

        databaseReference.addValueEventListener(object : ValueEventListener{
            var found=false
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dsnapshot:DataSnapshot  in snapshot.getChildren()){
                    val result= dsnapshot.child("name").getValue().toString();

                    if(result==name)
                    {
                        hideProgressbar()

                        found=true
                        mechanic=dsnapshot.getValue(Mechanic::class.java)!!
                            binding.mechanicName.setText(mechanic.name)
                            binding.nameOfMechanic.setText(mechanic.name)
                            binding.phNo.setText(mechanic.phoneNo)
                            binding.passion.setText(mechanic.profession)
//                            getUrlProfile(dsnapshot.toString())

                    }
                }

                if(found==false)
                {
                    hideProgressbar()
                    Toast.makeText(this@Mechanic_Info,"Failed to find name",Toast.LENGTH_SHORT).show()

                }


            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressbar()
                Toast.makeText(this@Mechanic_Info,"Failed to add data",Toast.LENGTH_SHORT).show()
            }
        })


    }
    fun getUrlProfile(imgName:String ) {
        storageReference=FirebaseStorage.getInstance().reference.child("Users/$imgName.jpg")
        val localFile= File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap=BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profileImage.setImageBitmap(bitmap)
            Toast.makeText(this@Mechanic_Info,"Profile pic uploaded",Toast.LENGTH_SHORT).show()

            hideProgressbar()
        }.addOnFailureListener{
            hideProgressbar()
            Toast.makeText(this@Mechanic_Info,"Failed to retreive image",Toast.LENGTH_SHORT).show()
        }
    }



    private fun showProgressBar(){
        dialog= Dialog(this@Mechanic_Info)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressbar(){
        dialog.dismiss()
    }
}