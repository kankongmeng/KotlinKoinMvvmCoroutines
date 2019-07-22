package com.brian.kotlinkoinmvvmcoroutines.activity

import android.os.Bundle
import com.brian.kotlinkoinmvvmcoroutines.fragment.MainFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setContentFragment(MainFragment.newInstance())
        }
    }
}
