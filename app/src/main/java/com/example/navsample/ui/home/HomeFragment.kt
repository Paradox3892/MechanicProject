package com.example.navsample.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navsample.MapsActivity
import com.example.navsample.Mechanics
import com.example.navsample.TroubleShooting
import com.example.navsample.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root:View=binding.root




        return root
    }
    override fun onStart() {
        super.onStart()
//        var txt = context.findViewById(R.id.txt_cat1) as TextView?
        trblsh_txt.setOnClickListener{
            val intent = Intent(context, TroubleShooting::class.java)
            startActivity(intent)
        }
        img_trbl.setOnClickListener{
            val intent = Intent(context, TroubleShooting::class.java)
            startActivity(intent)
        }
        mechanics_call_txt.setOnClickListener{
            val intent = Intent(context, Mechanics::class.java)
            startActivity(intent)
        }
        mechanics_call_img.setOnClickListener{
            val intent = Intent(context, Mechanics::class.java)
            startActivity(intent)
        }
        location_txt.setOnClickListener{
            val intent = Intent(context, MapsActivity::class.java)
            startActivity(intent)
        }
    }

//    private fun moveToNewActivity() {
//        val i = Intent(activity, MapsActivity::class.java)
//        startActivity(i)
//        (activity as Activity?)!!.overridePendingTransition(0, 0)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}