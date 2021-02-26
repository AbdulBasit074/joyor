package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.LoginLayoutBinding
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
                binding.sureName.visibility = View.GONE
                binding.name.visibility = View.GONE
                binding.email.visibility = View.VISIBLE
                binding.password.visibility = View.VISIBLE

            } else {
                binding.loginBtn.text = "Register"
                binding.login.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.round_border_white)
                binding.signUp.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.round_border_brown)
                binding.sureName.visibility = View.VISIBLE
                binding.name.visibility = View.VISIBLE
                binding.email.visibility = View.VISIBLE
                binding.password.visibility = View.VISIBLE

            }
        })

    }
}

