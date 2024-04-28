package com.chaeda.chaeda.presentation.splash

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivitySplashBinding
import com.chaeda.chaeda.presentation.login.LoginActivity
import com.chaeda.chaeda.util.NetworkManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity
    : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
        }
        checkNetwork()
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
            startActivity(LoginActivity.getIntent(this))
        }, 3000)
    }
}