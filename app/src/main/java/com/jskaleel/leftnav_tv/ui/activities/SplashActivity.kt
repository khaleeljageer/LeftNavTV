package com.jskaleel.leftnav_tv.ui.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.jskaleel.leftnav_tv.R
import com.jskaleel.leftnav_tv.view_model.SplashViewModel

class SplashActivity : FragmentActivity() {

    private val splashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.listenPageChange()
        splashViewModel.changePage.observe(this, {
            if (it) {
                startActivity(MainTabsActivity.getNewIntent(baseContext))
                finish()
            }
        })
    }
}