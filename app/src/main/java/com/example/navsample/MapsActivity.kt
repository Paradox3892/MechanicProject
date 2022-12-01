package com.example.navsample

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_login.*


 class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    var ToyotaBaguioCity = LatLng(16.423316, 120.591685)
    var REValdezAutoRepairShop = LatLng(16.41949023629486, 120.5927718408471)
    var AUTOBARTSAUTOSHOP = LatLng(16.41829061917717, 120.59441694670457)
    var J2BrothersAutoRepairShop = LatLng(16.418177745840165, 120.5861676361758)
    var KrisAutoRepairShop = LatLng(16.411965523958326, 120.58441266414715)
    var TheSistersAutoSupplyAndMechanicWorks
    = LatLng(16.418109736032097, 120.61540369907807)
    var RollyAutoRepairShop = LatLng(16.420143566782812, 120.60586236165243)
    var SquareJAutomotiveRepairShop
    = LatLng(16.3774845015872, 120.6260131502399)
    var JABLAutoBodyandRepairShop
    = LatLng(16.38058105971992, 120.6122036088899)
    var JonarsAutoRepairShopandCarWash = LatLng(16.379881110782904, 120.60915661946295)
    var GRTuningAutoshop = LatLng(16.38313379345876, 120.60409260882875)
    var WQLAUTOREPAIRSHOP
    = LatLng(16.395444087915884, 120.59984398975537)
    var AutoRepairShop
    = LatLng(16.397296740548832, 120.59825612202035)
    var RcaAutoRepairShop
    = LatLng(16.431144969491083, 120.60701460588689)
    var OTOMIKEAutoUnderchassisRepairShop = LatLng (16.416050334989112, 120.55799355694144)
    var MannysTouchAutoRepairShop
    = LatLng (16.410078329421765, 120.56549462329818)
    var RapideMetroBaguioAutoRepairCarService = LatLng (16.388953880415404, 120.55861121043372)
    var ZamAutoRepairShop
    = LatLng (16.42496092041772, 120.59668940582387)
    var CastrolCarWorkshopAutoshackInc
    = LatLng (16.41648095021488, 120.58544558566962)
    var JOJOSRADIATORSHOP
    = LatLng (16.414485306891248, 120.58758172982243)


    private var names = arrayOf("ToyotaBaguioCity","RE Valdez Auto Repair Shop",
        "AUTOBARTS AUTOSHOP","J2 Brothers Auto Repair Shop","Kris Auto Repair Shop","The Sister's Auto Supply And Mechanic Works",
        "Rolly Auto Repair Shop","Square J Automotive Repair Shop","JA-BL Auto Body & Repair Shop","Jonar's Auto Repair Shop and Car Wash",
        "GR Tuning Autoshop","WQL AUTO REPAIR SHOP","Auto Repair Shop","Rca Auto Repair Shop","OTOMIKE Auto Underchassis Repair Shop",
        "Manny's Touch Auto Repair Shop","Rapide Metro Baguio - Auto Repair, Car Service","Zam Auto Repair Shop",
        "Castrol Car Workshop- Autoshack Inc.","JOJO'S RADIATOR SHOP")

    private var locationArrayList: ArrayList<LatLng>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        locationArrayList = ArrayList()


        locationArrayList!!.add(ToyotaBaguioCity)
        locationArrayList!!.add(REValdezAutoRepairShop)
        locationArrayList!!.add(AUTOBARTSAUTOSHOP)
        locationArrayList!!.add(J2BrothersAutoRepairShop)
        locationArrayList!!.add(KrisAutoRepairShop)
        locationArrayList!!.add(TheSistersAutoSupplyAndMechanicWorks)
        locationArrayList!!.add(RollyAutoRepairShop)
        locationArrayList!!.add(SquareJAutomotiveRepairShop)
        locationArrayList!!.add(JABLAutoBodyandRepairShop)
        locationArrayList!!.add(JonarsAutoRepairShopandCarWash)
        locationArrayList!!.add(GRTuningAutoshop)
        locationArrayList!!.add(WQLAUTOREPAIRSHOP)
        locationArrayList!!.add(AutoRepairShop)
        locationArrayList!!.add(RcaAutoRepairShop)
        locationArrayList!!.add(OTOMIKEAutoUnderchassisRepairShop)
        locationArrayList!!.add(MannysTouchAutoRepairShop)
        locationArrayList!!.add(RapideMetroBaguioAutoRepairCarService)
        locationArrayList!!.add(ZamAutoRepairShop)
        locationArrayList!!.add(CastrolCarWorkshopAutoshackInc)
        locationArrayList!!.add(JOJOSRADIATORSHOP)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.uiSettings.setZoomControlsEnabled(true);
        for (i in locationArrayList!!.indices) {

            mMap!!.addMarker(MarkerOptions().position(locationArrayList!![i]).title(names[i]))

            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15.0f))

            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(this.ToyotaBaguioCity, 16.4023f))

            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!![i]))
        }
    }
}
