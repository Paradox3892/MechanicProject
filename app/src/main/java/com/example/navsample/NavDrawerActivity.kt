package com.example.navsample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navsample.databinding.ActivityNavDrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class NavDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavDrawerBinding
    private  var name=""
    private  var username=""
    private lateinit var email:String
    var mAuth: FirebaseAuth? = null
    var currentUser: FirebaseUser? = null
    private lateinit var sharedPref : SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser

        sharedPref = getSharedPreferences("LoginInfo" , Context.MODE_PRIVATE)


        name = sharedPref.getString("name" , "No imported" ).toString()
        username = sharedPref.getString("username" , "No imported" ).toString()

//
//        username = intent.getStringExtra("username").toString()
//        name = intent.getStringExtra("name").toString()




        supportActionBar?.title = Html.fromHtml("<font color=\"black\">"+getString(R.string.app_name)+ "</font>")

        binding = ActivityNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavDrawer.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val actionBar=supportActionBar
        actionBar!!.title="Categories"

        updateNavHeader()


    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


   private fun updateNavHeader() {
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val Name: TextView = headerView.findViewById(R.id.nameTxtF)
        val navUserName: TextView = headerView.findViewById(R.id.userTxtF)
       val Email: TextView = headerView.findViewById(R.id.emailTxtF)

       val btn:TextView=headerView.findViewById(R.id.Logout)
       btn.setOnClickListener{
           mAuth?.signOut()
           val i = Intent(this@NavDrawerActivity, Login::class.java)
           startActivity(i)
       }
       Email.text = currentUser!!.email

            Name.text=name
            navUserName.text=username
    }
    override fun onBackPressed() {
        mAuth?.signOut()
        val i = Intent(this@NavDrawerActivity, Login::class.java)
        startActivity(i)
    }

}