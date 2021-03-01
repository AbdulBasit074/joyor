package com.joyor.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joyor.R
import com.joyor.databinding.MainLayoutBinding
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private var isMenuOpen: Boolean = false
    private lateinit var binding: MainLayoutBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var fragmentTransition: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewModelDetail = viewModel
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
        viewModel.user.observe(this, Observer {
            if (it != null) {
                binding.profileMenu.login.text = "Logout"
                viewModel.isUserLogin = true
            }
            if (it == null) {
                binding.profileMenu.login.text = "Login"
                viewModel.isUserLogin = false
            }
        })
        viewModel.isProfileClick.observe(this, Observer { it ->
            if (it) {
                binding.profileMenu.menuLayout.visibility = View.VISIBLE
                if (viewModel.isUserLogin) {
                    loadFragment(GpsFragment())
                    binding.profileMenu.gps.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.brown
                        )
                    )
                    binding.profileMenu.login.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )

                } else {
                    loadFragment(LoginFragment())
                    binding.profileMenu.login.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.brown
                        )
                    )
                    binding.profileMenu.gps.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                }
                viewModel.isProfileClick.value = false
                isMenuOpen = true
                binding.back.visibility = View.VISIBLE
            }
        })
        viewModel.isBackClick.observe(this, Observer { it ->

            if (it) {
                this.supportFragmentManager.popBackStack()
                viewModel.isBackClick.value = false
                loginBack()
            }
        })
        viewModel.isLoginClick.observe(this, Observer {

            if (it) {
                binding.profileMenu.login.setTextColor(ContextCompat.getColor(this, R.color.brown))
                binding.profileMenu.gps.setTextColor(ContextCompat.getColor(this, R.color.white))
                if (viewModel.isUserLogin) {
                    JoyorDb.newInstance(this).userDao().logOut()
                    viewModel.isUserLogin = false
                    this.supportFragmentManager.popBackStack()
                    loginBack()
                } else {
                    loadFragment(LoginFragment())
                    menuOpen()
                }
            }
        })
        viewModel.isGpsClick.observe(this, Observer {
            if (it) {
                binding.profileMenu.gps.setTextColor(ContextCompat.getColor(this, R.color.brown))
                binding.profileMenu.login.setTextColor(ContextCompat.getColor(this, R.color.white))
                loadFragment(GpsFragment())
                menuOpen()
            }
        })
        setBottomNavigationListener()
        loadFragment(ProductFragment())
    }

    private fun menuOpen() {
        isMenuOpen = true
        binding.profileMenu.menuLayout.visibility = View.VISIBLE
        binding.back.visibility = View.VISIBLE
    }

    private fun loginBack() {
        isMenuOpen = false
        binding.profileMenu.menuLayout.visibility = View.INVISIBLE
        binding.back.visibility = View.INVISIBLE
    }

    private fun setBottomNavigationListener() {
        val mBottomNavigationListener =
            BottomNavigationView.OnNavigationItemSelectedListener { it ->
                loginBack()
                when (it.itemId) {
                    R.id.cart -> {
                        loadFragment(ProductFragment())
                        it.setIcon(R.drawable.ic_cart_active)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.setting -> {
                        loadFragment(SettingFragment())
                        it.setIcon(R.drawable.ic_setting_active)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.support -> {
                        it.setIcon(R.drawable.ic_support_active)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.order -> {
                        loadFragment(OrderFragment())
                        it.setIcon(R.drawable.ic_order_active)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.location -> {
                        loadFragment(LocationFragment())
                        it.setIcon(R.drawable.ic_location_active)
                        return@OnNavigationItemSelectedListener true
                    }


                }
                return@OnNavigationItemSelectedListener false
            }
        binding.bottomNavigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)

    }

    private fun loadFragment(fragment: Fragment) {
        if (isMenuOpen)
            this.supportFragmentManager.popBackStack()
        fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.frameContainer.id, fragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    override fun onBackPressed() {
        if (isMenuOpen) {
            binding.back.performClick()
        } else {
            this.finish()
        }
    }
}