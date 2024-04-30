package com.chaeda.chaeda.presentation.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()

        this.window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFFFFF")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun initListener() {
        with(binding) {
            tvLogin.setOnSingleClickListener {
                startActivity(MainActivity.getIntent(this@LoginActivity))
                finish()
            }
            tvSignup.setOnSingleClickListener {
                startActivity(SignUpActivity.getIntent(this@LoginActivity))
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}