package com.brian.kotlinkoinmvvmcoroutines.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.brian.kotlinkoinmvvmcoroutines.activity.BaseActivity
import com.brian.kotlinkoinmvvmcoroutines.viewmodel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<out ViewModelType : BaseViewModel>(clazz: KClass<ViewModelType>) :
    Fragment() {
    val viewModel: ViewModelType by viewModel(clazz)

    private fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observe showRefreshing value to display or hide progressBar
        viewModel.showRefreshing.observe(this, Observer { showLoading ->
            setRefreshing(showLoading)
        })
    }

    fun setRefreshing(refreshing: Boolean) {
        getBaseActivity().setRefreshing(refreshing)
    }
}
