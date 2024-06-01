package com.chaeda.chaeda.presentation.splash

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivitySplashBinding
import com.chaeda.chaeda.presentation.MainActivity
import com.chaeda.chaeda.presentation.login.LoginActivity
import com.chaeda.chaeda.presentation.setting.SettingState
import com.chaeda.chaeda.presentation.setting.SettingViewModel
import com.chaeda.chaeda.util.NetworkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity
    : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
        }
        checkNetwork()
        observe()
    }

    private fun checkNetwork() {
        if (NetworkManager.checkNetworkState(this)) initSplash()
        else {
            AlertDialog.Builder(this)
                .setTitle("인터넷 연결")
                .setMessage("인터넷 연결을 확인해주세요.")
                .setCancelable(false)
                .setPositiveButton(
                    "확인",
                ) { _, _ ->
                    finishAffinity()
                }
                .create()
                .show()
        }
    }

    private fun initSplash() {
//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(MainActivity.getIntent(this))
//        }, 3000)
        Handler(Looper.getMainLooper()).postDelayed({
            if (viewModel.getAutoLogin()) viewModel.getMember()
            else {
                startActivity(LoginActivity.getIntent(this@SplashActivity))
                finish()
            }
        }, 3000)
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.settingState.collect { state ->
                when (state) {
                    is SettingState.GetMemberSuccess -> {
                        startActivity(MainActivity.getIntent(this@SplashActivity))
                        finish()
                    }
                    is SettingState.Failure -> {
                        startActivity(LoginActivity.getIntent(this@SplashActivity))
                        finish()
                    }
                    else -> { }
                }
            }
        }
    }
}