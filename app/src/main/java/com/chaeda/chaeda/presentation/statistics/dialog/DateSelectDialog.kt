package com.chaeda.chaeda.presentation.statistics.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
    private val weekDates = ArrayList<String>()

    init {
        this.date = date
        this.dateSelectDialogInterface = dateSelectDialogInterface
        this.mode = mode
        if (mode == MODE_MONTH) this.date = LocalDate.of(date.year, date.monthValue, 1)
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

            initDateNumberPicker()

            when (mode) {
                MODE_DATE -> initForModeDate()
                MODE_WEEK -> initForModeWeek()
                MODE_MONTH -> initForModeMonth()
            }

        }
    }

    private fun initDateNumberPicker() {
        with(binding) {
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

            npDate.visibility = View.VISIBLE
            when (mode) {
                MODE_DATE -> {
                    npDate.minValue = 1
                    npDate.maxValue = 31
                    npDate.value = date.dayOfMonth
                }
                MODE_WEEK -> {
                    weekDates.clear()
                    for (i in 1 .. YearMonth.of(date.year, date.monthValue).lengthOfMonth()) {
                        val cur = LocalDate.of(date.year, date.monthValue, i)
                        if (cur.dayOfWeek.value == 1 && cur.isBefore(LocalDate.now())) {
                            weekDates.add("$i")
                        }
                    }
                    if (weekDates.size == 0) {
                        weekDates.clear()
                        date = date.minusMonths(1)
                        date = LocalDate.of(date.year, date.monthValue, YearMonth.of(date.year, date.monthValue).lengthOfMonth())
                        for (i in 1 .. YearMonth.of(date.year, date.monthValue).lengthOfMonth()) {
                            val cur = LocalDate.of(date.year, date.monthValue, i)
                            if (cur.dayOfWeek.value == 1 && cur.isBefore(LocalDate.now())) {
                                weekDates.add("$i")
                            }
                        }
                    }
                    npDate.minValue = 0
                    npDate.maxValue = weekDates.size - 1
                    npDate.displayedValues = weekDates.toTypedArray()
                    npDate.value = 0
                }
                MODE_MONTH -> {
                    npDate.value = 1
                    npDate.visibility = View.GONE
                }
            }

        }
    }

    private fun initForModeDate() {
        updateDateNumberPicker()

        with(binding) {
            npYear.setOnValueChangedListener { _, _, newYear ->
                if (date.dayOfMonth > YearMonth.of(newYear, date.monthValue).lengthOfMonth()) {
                    date = LocalDate.of(newYear, date.monthValue, YearMonth.of(newYear, date.monthValue).lengthOfMonth())
                } else
                    date = LocalDate.of(newYear, date.monthValue, date.dayOfMonth)
                validateDateNumber()
                updateDateNumberPicker()
            }

            npMonth.setOnValueChangedListener { _, _, newMonth ->
                if (date.dayOfMonth > YearMonth.of(date.year, newMonth).lengthOfMonth()) {
                    date = LocalDate.of(date.year, newMonth, YearMonth.of(date.year, newMonth).lengthOfMonth())
                } else
                    date = LocalDate.of(date.year, newMonth, date.dayOfMonth)
                validateDateNumber()
                updateDateNumberPicker()
            }

            npDate.setOnValueChangedListener { _, _, newDate ->
                date = LocalDate.of(date.year, date.monthValue, newDate)
                validateDateNumber()
            }
        }
    }

    private fun initForModeWeek() {
        updateDateNumberPicker()

        with(binding) {
            npYear.setOnValueChangedListener { _, _, newYear ->
                if (date.dayOfMonth > YearMonth.of(newYear, date.monthValue).lengthOfMonth()) {
                    date = LocalDate.of(newYear, date.monthValue, YearMonth.of(newYear, date.monthValue).lengthOfMonth())
                } else
                    date = LocalDate.of(newYear, date.monthValue, date.dayOfMonth)
                validateDateNumber()
                updateDateNumberPicker()
                updateWeekDates(false)
            }

            npMonth.setOnValueChangedListener { _, _, newMonth ->
                if (date.dayOfMonth > YearMonth.of(date.year, newMonth).lengthOfMonth()) {
                    date = LocalDate.of(date.year, newMonth, YearMonth.of(date.year, newMonth).lengthOfMonth())
                } else
                    date = LocalDate.of(date.year, newMonth, date.dayOfMonth)
                validateDateNumber()
                updateDateNumberPicker()
                updateWeekDates(false)
            }

            npDate.setOnValueChangedListener { _, _, newDate ->
                Log.d("chaeda-npdate", "newDate: $newDate")
                date = LocalDate.of(date.year, date.monthValue, weekDates[newDate].toInt())
                validateDateNumber()
                updateWeekDates(true)
            }
        }
    }

    private fun updateWeekDates(isDate: Boolean) {
        weekDates.clear()
        for (i in 1 .. YearMonth.of(date.year, date.monthValue).lengthOfMonth()) {
            val cur = LocalDate.of(date.year, date.monthValue, i)
            if (cur.dayOfWeek.value == 1 && cur.isBefore(LocalDate.now())) {
                weekDates.add("$i")
            }
        }
        if (weekDates.size == 0) {
            weekDates.clear()
            date = date.minusMonths(1)
            date = LocalDate.of(date.year, date.monthValue, YearMonth.of(date.year, date.monthValue).lengthOfMonth())
            for (i in 1 .. YearMonth.of(date.year, date.monthValue).lengthOfMonth()) {
                val cur = LocalDate.of(date.year, date.monthValue, i)
                if (cur.dayOfWeek.value == 1 && cur.isBefore(LocalDate.now())) {
                    weekDates.add("$i")
                }
            }
        }
        with(binding) {
            Log.d("chaeda-numberpicker", "npDate - value: ${npDate.value} => ${npDate.displayedValues[npDate.value]}, maxValue: ${npDate.maxValue}")
//            npDate.value = 0
            if (!isDate) {
                npDate.value = 0
                npDate.maxValue = 0
            }
            Log.d("chaeda-numberpicker", "npDate - value: ${npDate.value} => ${npDate.displayedValues[npDate.value]}, maxValue: ${npDate.maxValue}")
            Log.d("chaeda-numberpicker", "weekdates: ${weekDates.toList()}, size: ${weekDates.size}")
//            if (npDate.maxValue <)
            npDate.displayedValues = weekDates.toTypedArray()
            npDate.maxValue = weekDates.size - 1
            Log.d("chaeda-numberpicker", "npDate - value: ${npDate.value} => ${npDate.displayedValues[npDate.value]}, maxValue: ${npDate.maxValue}")
//            npDate.value = npDate.maxValue
            Log.d("chaeda-numberpicker", "npDate - value: ${npDate.value} => ${npDate.displayedValues[npDate.value]}, maxValue: ${npDate.maxValue}")
            date = LocalDate.of(date.year, date.monthValue, weekDates[npDate.value].toInt())
        }
    }

    private fun initForModeMonth() {
        updateDateNumberPicker()

        with(binding) {
            npYear.setOnValueChangedListener { _, _, newYear ->
                date = LocalDate.of(newYear, date.monthValue, 1)
                validateDateNumber()
                updateDateNumberPicker()
            }

            npMonth.setOnValueChangedListener { _, _, newMonth ->
                date = LocalDate.of(date.year, newMonth, 1)
                validateDateNumber()
                updateDateNumberPicker()
            }
        }
    }

    private fun validateDateNumber() {
        if (date.isAfter(LocalDate.now())) {
            date = LocalDate.now()
            if (mode == MODE_MONTH) date = LocalDate.of(date.year, date.monthValue, 1)
        }
    }

    private fun updateDateNumberPicker() {
        with(binding) {
            npYear.value = date.year
            npMonth.value = date.monthValue

            val today = LocalDate.now()
            if (mode == MODE_DATE) {
                npDate.value = date.dayOfMonth
                npDate.maxValue = YearMonth.of(date.year, date.monthValue).lengthOfMonth()
            }
            if (npYear.value == today.year) {
                npMonth.maxValue = today.monthValue
                if (mode == MODE_DATE && npMonth.value == today.monthValue) {
                    npDate.maxValue = today.dayOfMonth
                }
            } else {
                npMonth.maxValue = 12
            }
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