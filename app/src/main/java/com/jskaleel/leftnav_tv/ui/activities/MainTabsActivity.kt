package com.jskaleel.leftnav_tv.ui.activities

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import coil.load
import coil.transform.BlurTransformation
import com.jskaleel.leftnav_tv.R
import com.jskaleel.leftnav_tv.ui.fragment.HomeFragment
import com.jskaleel.leftnav_tv.ui.fragment.SearchFragment
import com.jskaleel.leftnav_tv.ui.fragment.SettingsFragment
import com.jskaleel.leftnav_tv.ui.widget.LeftMenuView
import kotlinx.android.synthetic.main.activity_maintabs.*
import kotlin.math.roundToInt


fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).roundToInt()
}

class MainTabsActivity : FragmentActivity(), LeftMenuView.MenuItemClickListener {

    private var leftMenusShown: Boolean = false
    private val maxExpandWidth = dpToPx(188)
    private val minExpandWidth = dpToPx(84)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintabs)

        rlLeftMenu.setupDefaultMenu(LeftMenuView.HOME_MENU)
        replaceSelectedFragment(HomeFragment.getInstance(), LeftMenuView.TAG_HOME)

        setupFocusListener()

        ivBg.load(R.drawable.splash_bg) {
            transformations(BlurTransformation(baseContext, 15f, 2f))
        }
    }

    companion object {
        fun getNewIntent(context: Context): Intent {
            return Intent(context, MainTabsActivity::class.java)
        }
    }

    override fun menuItemClick(menuId: Int) {
        if (rlLeftMenu.getCurrentSelected() == menuId) {
            return
        }
        val fragment: Fragment?
        val tag: String
        when (menuId) {
            LeftMenuView.SEARCH_MENU -> {
                rlLeftMenu.setCurrentSelected(LeftMenuView.SEARCH_MENU)
                fragment = SearchFragment.getInstance()
                tag = LeftMenuView.TAG_SEARCH
            }
            LeftMenuView.SETTING_MENU -> {
                rlLeftMenu.setCurrentSelected(LeftMenuView.SETTING_MENU)
                fragment = SettingsFragment.getInstance()
                tag = LeftMenuView.TAG_SETTINGS
            }
            else -> {
                rlLeftMenu.setCurrentSelected(LeftMenuView.HOME_MENU)
                fragment = HomeFragment.getInstance()
                tag = LeftMenuView.TAG_HOME
            }
        }

        leftMenusShown = false
        hideLeftMenu(rlLeftMenu)
        Handler(Looper.getMainLooper()).postDelayed({
            replaceSelectedFragment(fragment, tag)
        }, 100)
        Handler(Looper.getMainLooper()).postDelayed({
            setupFocusListener()
        }, 300)
    }

    private fun replaceSelectedFragment(fragment: Fragment?, tag: String) {
        browseFrameLayout.removeAllViewsInLayout()
        supportFragmentManager.beginTransaction()
            .replace(R.id.browseFrameLayout, fragment!!)
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            .commit()

        browseFrameLayout.requestFocus()
    }

    private fun setupFocusListener() {
        rlLeftMenu.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                rlLeftMenu.onFocusChangeListener = null
                leftMenusShown = true
                showLeftMenu(view)
            } else {
                leftMenusShown = false
                hideLeftMenu(view)
            }
        }
    }

    private fun showLeftMenu(view: View) {
        val width = rlLeftMenu.measuredWidth
        val valueAnimator = ValueAnimator.ofInt(width, maxExpandWidth)
        rlLeftMenu.setupMenuExpandedUI()

        transView.visibility = View.VISIBLE
        LeftMenuView.animateView(view, valueAnimator)
    }

    private fun hideLeftMenu(view: View) {
        val width = rlLeftMenu.measuredWidth
        val valueAnimator = ValueAnimator.ofInt(width, minExpandWidth)
        rlLeftMenu.setupMenuClosedUI()

        transView.visibility = View.GONE
        LeftMenuView.animateView(view, valueAnimator)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return if (leftMenusShown) {
                resetLeftMenuUI()
            } else {
                onBackPressed()
                return false
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && leftMenusShown) {
            return resetLeftMenuUI()
        }
        return false
    }

    private fun resetLeftMenuUI(): Boolean {
        leftMenusShown = false
        browseFrameLayout.requestFocus()
        hideLeftMenu(rlLeftMenu)
        setupFocusListener()
        return true
    }

    override fun onDestroy() {
        rlLeftMenu.onFocusChangeListener = null
        super.onDestroy()
    }
}