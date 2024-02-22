package com.chaeda.chaeda.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityMainBinding
import com.chaeda.chaeda.presentation.home.HomeFragment
import com.chaeda.chaeda.presentation.homework.HomeworkFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity
    : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBnvItemSelectedListener()
        setTabLayout()
        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun setTabLayout() {

    }

    private fun initBnvItemSelectedListener() {
        supportFragmentManager.findFragmentById(R.id.fcv_main) ?: navigateTo<HomeFragment>()
        binding.bnvMain.selectedItemId = R.id.menu_home
        binding.bnvMain.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_home -> {
                    navigateTo<HomeFragment>()
                }

                R.id.menu_homework -> navigateTo<HomeworkFragment>()
                R.id.menu_analysis -> {
//                    navigateTo<AnalysisFragment>()
                }
            }
            true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.java.canonicalName)
        }
    }
}