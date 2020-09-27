package com.jskaleel.leftnav_tv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jskaleel.leftnav_tv.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txtView.requestFocus()
    }

    companion object {
        fun getInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}