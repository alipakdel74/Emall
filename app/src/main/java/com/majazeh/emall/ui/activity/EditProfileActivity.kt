package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.doOnTextChanged
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.majazeh.emall.R
import com.majazeh.emall.databinding.EditProfileBinding
import com.majazeh.emall.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : BindingActivity<EditProfileBinding>(), OnMapReadyCallback {

    private val vm by viewModel<EditProfileViewModel>()
    private lateinit var mMap: GoogleMap

    private var latitude = 0.0
    private var longitude = 0.0

    override fun getLayoutResId(): Int = R.layout.activity_edit_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        binding.vm = vm

        val mapFragment = supportFragmentManager.findFragmentById(R.id.ediMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.edtEmail.doOnTextChanged { text, _, _, _ ->
            vm.mEmail.set(text.toString().trim())
        }
        binding.edtMobileNumber.doOnTextChanged { text, _, _, _ ->
            vm.mNumber.set(text.toString().trim())
        }
        binding.edtName.doOnTextChanged { text, _, _, _ ->
            vm.mName.set(text.toString().trim())
        }
        binding.edtPassword.doOnTextChanged { text, _, _, _ ->
            vm.mPassword.set(text.toString().trim())
        }
        binding.btnChangeAddress.setOnClickListener {

            startActivityForResult(
                Intent(this, MapsActivity::class.java)
                    .putExtra("latitude", latitude)
                    .putExtra("longitude", longitude)
                    .putExtra("userAddress", true), 0
            )
        }

        var flag = false
        binding.inputPassword.setStartIconOnClickListener {
            runOnUiThread {
                if (!flag) {
                    binding.inputPassword.setStartIconDrawable(R.drawable.ic_eye_visibility)
                    binding.edtPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    flag = true
                } else {
                    binding.edtPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.inputPassword.setStartIconDrawable(R.drawable.ic_eye_invisible)
                    flag = false
                }
                binding.edtPassword.setSelection(binding.edtPassword.text!!.length)
            }
        }

        vm.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })
        vm.toast.observe(this, {
            SnackBarBuilder(getString(it)).show(this)
        })

        vm.edit.observe(this, {
            val intent = Intent()
            intent.putExtra("EditUser", it)
            setResult(RESULT_OK, intent)
            finish()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.action_menu).setIcon(R.drawable.ic_back)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_menu) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map!!
        vm.me.observe(this, {
            binding.user = it
            if (it.address.isNullOrEmpty())
                binding.btnChangeAddress.text = getString(R.string.selectAddress)
            else {
                vm.mAddress.set(it.address)
                binding.btnChangeAddress.text = getString(R.string.changeAddress)
                val lat = it.address!!.substring(0, it.address!!.lastIndexOf(","))
                val lng = it.address!!.substring(it.address!!.indexOf(",") + 1, it.address!!.length)
                mMap.addMarker(MarkerOptions().position(LatLng(lat.toDouble(), lng.toDouble())))
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            lat.toDouble(),
                            lng.toDouble()
                        ), 17f
                    )
                )
                latitude = lat.toDouble()
                longitude = lng.toDouble()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val lat = data?.getDoubleExtra("latitude", 0.0)
            val lng = data?.getDoubleExtra("longitude", 0.0)
            if (lat == null || lat <= 0)
                binding.btnChangeAddress.text = getString(R.string.selectAddress)
            else {
                binding.btnChangeAddress.text = getString(R.string.changeAddress)
                mMap.addMarker(MarkerOptions().position(LatLng(lat, lng!!)))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 17f))
                vm.mAddress.set("$lat,$lng")
            }
        }
    }

}