package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.LoginLayoutBinding
import com.joyor.helper.moveToAndFinish
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.LoginSignUpViewModel


class LoginFragment : Fragment() {


    private lateinit var binding: LoginLayoutBinding
    private lateinit var viewModel: LoginSignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.login_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginSignUpViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.isLogin.observe(this, Observer { it ->

            if (it) {
                binding.loginBtn.text = "Login"
                binding.login.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.round_border_brown)
                binding.signUp.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.round_border_white)
                binding.login.setTextColor(getColor(activity!!, R.color.white))
                binding.signUp.setTextColor(getColor(activity!!, R.color.black))
                binding.sureName.visibility = View.INVISIBLE
                binding.name.visibility = View.INVISIBLE
                binding.email.visibility = View.VISIBLE
                binding.password.visibility = View.VISIBLE

            } else {
                binding.loginBtn.text = "Register"
                binding.login.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.round_border_white)
                binding.login.setTextColor(getColor(activity!!, R.color.black))
                binding.signUp.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.round_border_brown)
                binding.signUp.setTextColor(getColor(activity!!, R.color.white))
                binding.sureName.visibility = View.VISIBLE
                binding.name.visibility = View.VISIBLE
                binding.email.visibility = View.VISIBLE
                binding.password.visibility = View.VISIBLE
            }
        })

        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })
        viewModel.user.observe(this, Observer {
            JoyorDb.newInstance(requireActivity()).userDao().logOut()
            JoyorDb.newInstance(requireActivity()).userDao().login(it)
            moveToAndFinish(HomeActivity::class.java)
        })

    }


}

