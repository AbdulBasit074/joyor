package com.joyor.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.adapter.CartAdapterRv
import com.joyor.databinding.CartFragmentBinding
import com.joyor.helper.CartUpdateEvent
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.moveTo
import com.joyor.helper.showToast
import com.joyor.model.CouponLines
import com.joyor.model.Product
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.CartViewModel
import org.greenrobot.eventbus.EventBus


class CartFragment : Fragment() {
    private var productCartList: ArrayList<Product> = ArrayList()
    private var productCartTotal: ArrayList<Product> = ArrayList()
    private lateinit var viewModel: CartViewModel
    private lateinit var binding: CartFragmentBinding
    private var subTotalAmount: Float = 0.0f
    private var totalAmount: Float = 0.0f
    private var discountAmount: Float = 0.0f
    private var discountPercentage: Float = 0.0f


    private lateinit var progressDialog: CustomProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.cart_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        viewModel.context = requireContext()
        binding.viewModel = viewModel
        progressDialog = CustomProgressBar(requireContext())
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productCartList.addAll(JoyorDb.newInstance(requireContext()).productDao().getAllCartProduct())
        viewModel.ischeckOut.observe(requireActivity(), Observer {
            var couponDetail = CouponLines(viewModel.returnCouponCode(), discountAmount.toString())
            if (discountAmount == 0.0f)
                couponDetail = CouponLines()
            moveTo(CheckOutActivity.newInstance(requireContext(), String.format("%.2f",totalAmount), couponDetail))
        })
        viewModel.progressBar.observe(requireActivity(), Observer {
            if (it)
                progressDialog.show()
            else
                progressDialog.dismiss()
        })
        viewModel.showToast.observe(requireActivity(), Observer {
            showToast(it)
        })
        viewModel.discountGot.observe(requireActivity(), Observer {
            if (it.toInt() > 0) {
                discountPercentage = it.toFloat()
                calculateDiscount()
            } else {
                binding.discountTitle.visibility = View.INVISIBLE
                binding.discountTotal.visibility = View.INVISIBLE
            }
        })
        setAdapter()
    }

    private fun calculateDiscount() {
        calculateTotalAmount()
        discountAmount = (subTotalAmount * (discountPercentage / 100))
        totalAmount = (totalAmount - discountAmount)
        binding.discountTotal.text = String.format("€%.2f", discountAmount)
        binding.totalPrice.text = String.format("€%.2f", totalAmount)
        binding.discountTitle.visibility = View.VISIBLE
        binding.discountTotal.visibility = View.VISIBLE
        binding.couponCode.setText("")
    }

    private fun setAdapter() {
        checkUiVisibility()
        binding.cartRv.adapter = CartAdapterRv(requireContext(), productCartList, viewModel) { adapterRefresh() }
        calculateTotalAmount()

    }

    private fun adapterRefresh() {
        productCartList.clear()
        productCartList.addAll(JoyorDb.newInstance(requireContext()).productDao().getAllCartProduct())
        checkUiVisibility()
        binding.cartRv.adapter?.notifyDataSetChanged()
        EventBus.getDefault().postSticky(CartUpdateEvent())
        calculateDiscount()
        calculateTotalAmount()

    }

    private fun checkUiVisibility() {
        if (productCartList.size < 1) {
            binding.noItem.visibility = View.VISIBLE
            binding.checkOutPhase.visibility = View.GONE
        } else {
            binding.noItem.visibility = View.GONE
            binding.checkOutPhase.visibility = View.VISIBLE
        }
    }

    private fun calculateTotalAmount() {
        productCartTotal.clear()
        productCartTotal.addAll(JoyorDb.newInstance(requireContext()).productDao().getAllCartProduct())
        subTotalAmount = 0.0f
        productCartTotal.forEach {
            subTotalAmount += (it.quantity * it.price!!.toInt())
        }
        binding.subTotal.text = String.format("€%.2f", subTotalAmount)
        totalAmount = (subTotalAmount - discountAmount)
        binding.totalPrice.text = String.format("€%.2f", totalAmount)
        if (productCartList.size < 1) {
            binding.checkOutPhase.visibility = View.INVISIBLE
        }
    }
}
