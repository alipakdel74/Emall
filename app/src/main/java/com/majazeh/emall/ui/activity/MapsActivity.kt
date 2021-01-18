package com.majazeh.emall.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.MapBinding
import com.majazeh.emall.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsActivity : BindingActivity<MapBinding>(), OnMapReadyCallback {

    private val vm by viewModel<MapViewModel>()

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var myLocation = ""

    override fun getLayoutResId(): Int = R.layout.activity_maps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            if (myLocation.isNotEmpty())
                vm.closeCart(myLocation)
            else SnackBarBuilder(getString(R.string.messageSelectLocation)).show(this)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        vm.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION)
        } else
            gpsAndNetworkIsOn()

        mMap.setOnMapClickListener {
            myLocation = ""
            mMap.clear()
            myLocation = "${it.latitude},${it.longitude}"
            mMap.addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)))
        }
    }

    private fun gpsAndNetworkIsOn() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        )
            MaterialAlertDialogBuilder(this)
                .setMessage(R.string.messageOnLocation)
                .setNeutralButton(R.string.closeDialog) { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }.setPositiveButton(R.string.activeLocation) { dialog, _ ->
                    dialog.dismiss()
                    startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),REQUEST_SETTING)
                }.create().show()
        else gotoMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun gotoMyLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mMap.isMyLocationEnabled = true
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val lastKnownLocation = task.result
                if (lastKnownLocation != null) {
                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude), 17f
                        )
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                gpsAndNetworkIsOn()
            else finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SETTING)
            gpsAndNetworkIsOn()
    }

    companion object {
        private const val REQUEST_PERMISSION = 25
        private const val REQUEST_SETTING = 3
    }
}