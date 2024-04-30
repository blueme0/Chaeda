package com.chaeda.chaeda.presentation.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityLoginBinding
import com.chaeda.chaeda.presentation.MainActivity
import com.chaeda.chaeda.presentation.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity
    : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFFFFF")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        binding.vm = loginViewModel
        binding.lifecycleOwner = this
        initListener()
        setTextChangedListener()
        observer()
    }

    private fun initListener() {
        with(binding) {
            tvLogin.setOnSingleClickListener {
                loginViewModel.postLogin()
            }
            tvSignup.setOnSingleClickListener {
                startActivity(SignUpActivity.getIntent(this@LoginActivity))
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
                    loginViewModel.updateId(p0.toString())
                }
            })

            etPw.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    loginViewModel.updatePw(p0.toString())
                }
            })
        }
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.loginState.collect { state ->
                when (state) {
                    is LoginUiState.Success -> {
                        startActivity(MainActivity.getIntent(this@LoginActivity))
                        finish()
                    }
                    is LoginUiState.Failure -> {
                        Toast.makeText(this@LoginActivity, state.msg, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}