package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.TechSupportFragmentBinding
import com.joyor.helper.*
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.TechSupportViewModel


class TechSupportFragment : Fragment() {


    private lateinit var binding: TechSupportFragmentBinding
    private lateinit var viewModel: TechSupportViewModel
    private lateinit var progressDialog: CustomProgressBar
    private var userRegisterProduct: ArrayList<com.joyor.model.RegisterProduct> = ArrayList()
    private var userRegisterProductModelList: ArrayList<String> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.tech_support_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TechSupportViewModel::class.java)

        progressDialog = CustomProgressBar(requireContext())
        viewModel.user.value = JoyorDb.newInstance(requireActivity()).userDao().getLoggedUser()
        viewModel.context = requireContext()
        binding.viewModel = viewModel
        viewModel.showToast.observe(requireActivity(), Observer {
            showToast(it)
        })
        viewModel.moveForLogin.observe(requireActivity(), Observer {
            if (it)
                moveTo(LoginActivity::class.java)
        })
        viewModel.sendOk.observe(requireActivity(), Observer {
            binding.registerProductDialog.text = ""
            binding.name.setText("")
            binding.email.setText("")
            binding.observation.setText("")
        })
        viewModel.user.observe(requireActivity(), Observer {

            val isRegister = Persister(requireContext()).getPersistedBoolean(Constants.userProductRegister, false)
            if (it != null && isRegister) {
                binding.techSupport.visibility = View.VISIBLE
                getRegisterProducts()
                binding.noteForTechSupport.visibility = View.GONE
                binding.noteForTechSupportRegister.visibility = View.GONE
            } else if (it != null && !isRegister) {
                binding.techSupport.visibility = View.GONE
                binding.noteForTechSupport.visibility = View.GONE
                binding.noteForTechSupportRegister.visibility = View.VISIBLE
            } else {
                binding.techSupport.visibility = View.GONE
                binding.noteForTechSupport.visibility = View.VISIBLE
                binding.noteForTechSupportRegister.visibility = View.GONE
            }

        })
        viewModel.moveForRegister.observe(requireActivity(), Observer {
            if (viewModel.user.value != null)
                moveTo(RegisterProduct::class.java)
        })
        viewModel.progressBar.observe(requireActivity(), Observer {
            if (it)
                progressDialog.show()
            else
                progressDialog.dismiss()
        })
        viewModel.selectProduct.observe(requireActivity(), Observer {
            if (it)
                registerProductSelect()
        })
    }

    private fun getRegisterProducts() {
        userRegisterProduct.clear()
        userRegisterProduct.addAll(JoyorDb.newInstance(requireContext()).registerProduct().getAllRegisterProduct())
    }

    private fun registerProductSelect() {
        /**select register product**/
        userRegisterProductModelList.clear()
        userRegisterProduct.forEach {
            userRegisterProductModelList.add(it.model!!)
        }
        val builder = AlertDialog.Builder(binding.root.context)
        val adapter = ArrayAdapter(binding.root.context, R.layout.support_simple_spinner_dropdown_item, userRegisterProductModelList)
        builder.setAdapter(adapter) { _, which ->
            binding.registerProductDialog.text = userRegisterProduct[which].model
            viewModel.registerSelectedProduct.value = userRegisterProduct[which]
        }
        val dialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.select_product)))
        dialog.show()
    }
    override fun onResume() {
        super.onResume()
        viewModel.user.value = JoyorDb.newInstance(requireActivity()).userDao().getLoggedUser()
    }
}

