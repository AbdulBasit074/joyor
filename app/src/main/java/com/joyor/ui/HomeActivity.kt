package com.joyor.ui

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joyor.R
import com.joyor.databinding.MainLayoutBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        setBottomNavigationListener()
        loadFragment(ProductFragment())
        setListener()
    }

    private fun setListener() {
        binding.profile.setOnClickListener {
            val inflater: LayoutInflater =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.top_right_menu, null)
            PopupWindow(
                view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).showAtLocation(binding.frameContainer, Gravity.END, 0, Gravity.TOP)

        }
    }

    private fun setBottomNavigationListener() {
        val mBottomNavigationListener =
            BottomNavigationView.OnNavigationItemSelectedListener { it ->

                when (it.itemId) {
                    R.id.cart -> {
                        loadFragment(ProductFragment())
                        it.setIcon(R.drawable.ic_cart_active)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.setting -> {
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
        val fragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.frameContainer.id, fragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

}