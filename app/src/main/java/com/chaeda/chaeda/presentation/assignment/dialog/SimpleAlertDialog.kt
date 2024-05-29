package com.chaeda.chaeda.presentation.assignment.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.DialogSimpleAlertBinding

class SimpleAlertDialog (
    simpleAlertDialogInterface: SimpleAlertDialogInterface
) : DialogFragment() {

    private var _binding: DialogSimpleAlertBinding? = null
    private val binding get() = _binding!!

    private var simpleAlertDialogInterface: SimpleAlertDialogInterface? = null

    init {
        this.simpleAlertDialogInterface = simpleAlertDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSimpleAlertBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initView()

        return view
    }

    private fun initView() {
        with(binding) {
            tvAccept.setOnSingleClickListener {
                simpleAlertDialogInterface?.onYesButtonClick()
                dismiss()
            }
        }
    }
}

interface SimpleAlertDialogInterface {
    fun onYesButtonClick()
}