package com.example.navsample


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.navsample.databinding.ActivityMechanicsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Mechanics : AppCompatActivity() {
    private val data = ArrayList<String>()
    private lateinit var binding: ActivityMechanicsBinding

    private lateinit var dialog: Dialog

    private lateinit var mechanic: Mechanic
    private var index = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mech: ArrayList<String>
    private lateinit var list: ArrayList<Mechanic>
    private lateinit var adapter: ArrayAdapter<Mechanic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mechanics)



        list = ArrayList()
        mech = ArrayList()
        mech.add(index, " ")
//        names.add("z")
        binding = ActivityMechanicsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">"+getString(R.string.app_name)+ "</font>"));
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("Mechanic")
        if (uid.isNotEmpty()) {
            getUserData()

        }


    }

    private fun generateListContent() {
        for (i in 0 until mech.size) {
            data.add("This is row number $i")
        }
    }

    private fun getUserData() {

        showProgressBar()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (dsnapshot: DataSnapshot in snapshot.getChildren()) {


                    mechanic = dsnapshot.getValue(Mechanic::class.java)!!
                    list.add(mechanic)
                }

                val lv = findViewById<View>(R.id.listMechanics) as ListView
                generateListContent()
                lv.adapter = MyListAdaper(this@Mechanics, R.layout.activity_mechanic_sample, list)
                hideProgressbar()
                lv.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        Toast.makeText(
                            this@Mechanics,
                            "List item was clicked at $position",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressbar()
                Toast.makeText(this@Mechanics, "Failed to add data", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun showProgressBar() {
        dialog = Dialog(this@Mechanics)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressbar() {
        dialog.dismiss()
    }

    private inner class MyListAdaper(
        context: Context,
        private val layout: Int,
        mObjects: ArrayList<Mechanic>
    ) :
        ArrayAdapter<Mechanic?>(context, layout, mObjects as List<Mechanic?>) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            var mainViewholder: ViewHolder? = null
            if (convertView == null) {
                val inflater = LayoutInflater.from(context)
                convertView = inflater.inflate(layout, parent, false)
                val viewHolder: ViewHolder = ViewHolder()

                viewHolder.button =
                    convertView.findViewById<View>(R.id.buttonMechanicCall) as Button
                convertView.tag = viewHolder
            }
            mainViewholder = convertView!!.tag as ViewHolder
            mainViewholder.button!!.text = list[position].name
            mainViewholder.button!!.setOnClickListener {
                val intent = Intent(context, Mechanic_Info::class.java)
                intent.putExtra("name", list[position].name)
                startActivity(intent)
//                Toast.makeText(
//                    context,
//                    "Button was clicked for list item $position",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
//                mainViewholder!!.title!!.text = getItem(position)
            return convertView
        }
    }

    inner class ViewHolder {
        var thumbnail: ImageView? = null
        var title: TextView? = null
        var button: Button? = null
    }
}
