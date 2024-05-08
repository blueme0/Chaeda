package com.chaeda.chaeda.presentation.statistics.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.DialogDateSelectBinding
import java.time.LocalDate
import java.time.YearMonth

class DateSelectDialog (
    dateSelectDialogInterface: DateSelectDialogInterface,
    date: LocalDate,
    mode: String
) : DialogFragment() {

    // define ViewBinding
    private var _binding: DialogDateSelectBinding? = null
    private val binding get() = _binding!!

    private var dateSelectDialogInterface: DateSelectDialogInterface? = null
    private var date: LocalDate = LocalDate.now()

    private var mode: String? = null

    init {
        this.date = date
        this.dateSelectDialogInterface = dateSelectDialogInterface
        this.mode = mode
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogDateSelectBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initView()

        return view
    }

    private fun initView() {
        with(binding) {
            tvAccept.setOnSingleClickListener {
                dateSelectDialogInterface?.onYesButtonClick(date!!)
                dismiss()
            }

            tvCancel.setOnSingleClickListener {
                dismiss()
            }

            npYear.wrapSelectorWheel = false
            npMonth.wrapSelectorWheel = false
            npDate.wrapSelectorWheel = false

            npYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            npMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            npDate.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            val currentDate: LocalDate = LocalDate.now()
            npYear.minValue = 2023
            npYear.maxValue = currentDate.year
            npYear.value = date.year

            npMonth.minValue = 1
            npMonth.maxValue = 12
            npMonth.value = date.monthValue

            npDate.minValue = 1
            npDate.maxValue = 31
            npDate.value = date.dayOfMonth

            updateDateNumberPicker()

            npYear.setOnValueChangedListener { _, _, newYear ->
                date = LocalDate.of(newYear, date.monthValue, date.dayOfMonth)
                updateDateNumberPicker()
            }

            npMonth.setOnValueChangedListener { _, _, newMonth ->
                if (date.dayOfMonth > YearMonth.of(date.year, newMonth).lengthOfMonth()) {
                    LocalDate.of(date.year, YearMonth.of(date.year, newMonth).lengthOfMonth(), date.dayOfMonth)
                } else {
                    date = LocalDate.of(date.year, newMonth, date.dayOfMonth)
                }
                updateDateNumberPicker()
            }

            npDate.setOnValueChangedListener { _, _, newDate ->
                date = LocalDate.of(date.year, date.monthValue, newDate)
            }
        }
    }

    private fun updateDateNumberPicker() {
        val currentDate: LocalDate = LocalDate.now()

        val selectedYear = binding.npYear.value
        val selectedMonth = binding.npMonth.value

        val yearMonth = YearMonth.of(selectedYear, selectedMonth)
        val lastDayOfMonth = yearMonth.lengthOfMonth()

        if (selectedYear == currentDate.year) {
            binding.npMonth.maxValue = currentDate.monthValue
            if (binding.npMonth.value > currentDate.monthValue) binding.npMonth.value = currentDate.monthValue
            if (selectedMonth == currentDate.monthValue) {
                binding.npDate.maxValue = currentDate.dayOfMonth
            }
            else {
                binding.npDate.maxValue = lastDayOfMonth
            }
        } else {
            binding.npMonth.maxValue = 12
            binding.npDate.maxValue = lastDayOfMonth
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MODE_DATE = "mode_date"
        private const val MODE_WEEK = "mode_week"
        private const val MODE_MONTH = "mode_month"
    }
}

interface DateSelectDialogInterface {
    fun onYesButtonClick(date: LocalDate)
}