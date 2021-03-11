package com.joyor.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joyor.R
import com.joyor.databinding.MainLayoutBinding
import com.joyor.databinding.TopRightMenuBinding
import com.joyor.helper.CartUpdateEvent
import com.joyor.helper.UserLoginEventBus
import com.joyor.helper.moveTo
import com.joyor.model.Product
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.HomeViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: MainLayoutBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var fragmentTransition: FragmentTransaction
    private lateinit var bindingMenu: TopRightMenuBinding
    private lateinit var popupWindow: PopupWindow
    private var productCartList: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        binding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewModelDetail = viewModel
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()

        viewModel.user.observe(this, Observer {
            if (it != null) {
                viewModel.isUserLogin = true
            }
            if (it == null) {
                viewModel.isUserLogin = false
            }
        })
        viewModel.isProfileClick.observe(this, Observer { it ->
            if (it) {

                bindingMenu = DataBindingUtil.inflate(layoutInflater, R.layout.top_right_menu, null, false)
                popupWindow = PopupWindow(bindingMenu.root, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
                if (viewModel.isUserLogin)
                    bindingMenu.login.text = getString(R.string.profile)
                else
                    bindingMenu.login.text = getString(R.string.login)
                popupWindow.showAsDropDown(binding.profile)
                bindingMenu.viewModel = viewModel
            }
        })

        viewModel.isLoginClick.observe(this, Observer {
            if (it) {
                if (!viewModel.isUserLogin)
                    moveTo(LoginActivity::class.java)
                else
                    moveTo(EditProfileActivity::class.java)
                popupWindow.dismiss()

            }
        })

        viewModel.isGpsClick.observe(this, Observer {
            if (it) {
                moveTo(GpsActivity::class.java)
                popupWindow.dismiss()

            }
        })
        setBottomNavigationListener()
        loadFragment(ProductFragment())
        onCartUpdated(CartUpdateEvent())
    }
    private fun setBottomNavigationListener() {
        val mBottomNavigationListener =
            BottomNavigationView.OnNavigationItemSelectedListener { it ->
                when (it.itemId) {
                    R.id.product -> {
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
                        loadFragment(TechSupportFragment())
                        it.setIcon(R.drawable.ic_support_active)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.cartMenu -> {
                        loadFragment(CartFragment())
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
        fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.frameContainer.id, fragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    override fun onBackPressed() {
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun userLoginEventBus(user: UserLoginEventBus) {
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartUpdated(event: CartUpdateEvent) {
        productCartList = JoyorDb.newInstance(this).productDao().getAllCartProduct() as ArrayList<Product>
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.cartMenu)
        badge.backgroundColor = getColor(R.color.white)
        badge.badgeTextColor = getColor(R.color.black)
        badge.verticalOffset = 10
        badge.number = productCartList.size
        badge.isVisible = productCartList.size > 0
    }
}
