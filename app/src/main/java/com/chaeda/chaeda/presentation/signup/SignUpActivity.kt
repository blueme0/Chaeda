package com.chaeda.chaeda.presentation.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity
    : BindingActivity<ActivitySignupBinding>(R.layout.activity_signup){

    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = signUpViewModel
        binding.lifecycleOwner = this
        initListener()
        setTextChangedListener()
        observer()
    }

    private fun initListener() {
        signUpViewModel.updateGender(1)
        with(binding) {
            btnFinish.isEnabled = false
            btnMale.setOnSingleClickListener { signUpViewModel.updateGender(1) }
            btnFemale.setOnSingleClickListener { signUpViewModel.updateGender(2) }

            val grades = HashMap<String, String>()
            grades["학년 선택하기"] = ""
            grades["N수생"] = "N_SU"
            grades["고1"] = "HIGH_1"
            grades["고2"] = "HIGH_2"
            grades["고3"] = "HIGH_3"
            grades["중1"] = "MIDDLE_1"
            grades["중2"] = "MIDDLE_2"
            grades["중3"] = "MIDDLE_3"
            grades["초1"] = "ELEMENT_1"
            grades["초2"] = "ELEMENT_2"
            grades["초3"] = "ELEMENT_3"
            grades["초4"] = "ELEMENT_4"
            grades["초5"] = "ELEMENT_5"
            grades["초6"] = "ELEMENT_6"

            binding.spinnerGrade.adapter = ArrayAdapter<String>(this@SignUpActivity, R.layout.item_spinner,
                grades.keys.toList().sortedWith(Comparator { t1, t2 ->
                    if (grades[t1]!!.length != grades[t2]!!.length) return@Comparator grades[t1]!!.length - grades[t2]!!.length
                    else return@Comparator grades[t1]!!.last().digitToInt() - grades[t2]!!.last().digitToInt()
                }))
            // Spinner에 OnItemSelectedListener 설정
            binding.spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                    // 선택된 아이템의 텍스트 가져오기
                    val selectedItem = parent.getItemAtPosition(position) as String
                    signUpViewModel.updateGrade(grades[selectedItem]!!)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // 아무 것도 선택되지 않은 경우 동작 (필요에 따라 구현 가능)
                }
            }

            btnFinish.setOnSingleClickListener {
                signUpViewModel.postSignUp()
            }

            tvSignup.setOnSingleClickListener {
                signUpViewModel.findInvalid()
            }
        }
    }

    private fun setTextChangedListener() {
        with(binding) {
            etId.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    signUpViewModel.updateId(p0.toString())
                }
            })

            etPw.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    signUpViewModel.updatePassword(p0.toString())
                }
            })

            etPwCheck.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    signUpViewModel.updatePasswordCheck(p0.toString())
                }
            })

            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    signUpViewModel.updateUserName(p0.toString())
                }
            })

            etNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    signUpViewModel.updatePhoneNumber(p0.toString())
                }
            })

            etSchool.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    signUpViewModel.updateSchoolName(p0.toString())
                }
            })
        }
    }


    private fun observer() {
        lifecycleScope.launchWhenStarted {
            signUpViewModel.gender.collect { state ->
                when (state) {
                    "MALE" -> {
                        binding.btnMale.setImageResource(R.drawable.ic_radio_checked)
                        binding.btnFemale.setImageResource(R.drawable.ic_radio_unchecked)
                    }
                    "FEMALE" -> {
                        binding.btnFemale.setImageResource(R.drawable.ic_radio_checked)
                        binding.btnMale.setImageResource(R.drawable.ic_radio_unchecked)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            signUpViewModel.inputsValid2.collect { state ->
                binding.btnFinish.isEnabled = state
            }
        }

        lifecycleScope.launchWhenStarted {
            signUpViewModel.signUpState.collect { state ->
                when (state) {
                    is SignUpUiState.Success -> {
                        finish()
                    }
                    is SignUpUiState.Failure -> {
                        Toast.makeText(this@SignUpActivity, state.msg, Toast.LENGTH_SHORT).show()
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SignUpActivity::class.java)
    }
}