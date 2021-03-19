package com.joyor.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.joyor.R
import com.joyor.databinding.DialogButtonBinding

class DialogButton(context: Context, private val title: String, private val onclickOk: () -> Unit) : Dialog(context) {


    private lateinit var binding: DialogButtonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_button, null, false)
        this.window!!.decorView.setBackgroundColor(Color.TRANSPARENT)
        setContentView(binding.root)
        binding.title.text = title
        binding.oKBtn.setOnClickListener {
            this.dismiss()
            onclickOk()
        }
    }
}