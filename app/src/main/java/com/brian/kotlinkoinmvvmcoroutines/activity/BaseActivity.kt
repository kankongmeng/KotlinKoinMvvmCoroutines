package com.brian.kotlinkoinmvvmcoroutines.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.brian.kotlinkoinmvvmcoroutines.R
import kotlinx.android.synthetic.main.layout_progress_overlay.*

abstract class BaseActivity : AppCompatActivity() {
    private val refreshingDebounceMs = 80L
    private var refreshingHandler: Handler = Handler()
    private var refreshingRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    /**
     * Sets the refreshing state of the Activity.
     *
     * @param refreshing Whether or not the activity should show refresh progress
     */
    fun setRefreshing(refreshing: Boolean) {
        if (refreshingRunnable != null) {
            refreshingHandler.removeCallbacks(refreshingRunnable)
        }
        refreshingRunnable = Runnable {
            progressOverlay?.visibility = if (refreshing) View.VISIBLE else View.GONE
        }
        refreshingHandler.postDelayed(
            { runOnUiThread(refreshingRunnable) }, refreshingDebounceMs
        )
    }

    /**
     * Sets the content fragment of this activity using a`replace`fragment transaction,
     * without adding it to the back stack.
     *
     * @param fragment An instance of a fragment to use as the content
     */
    protected fun setContentFragment(fragment: Fragment) {
        navigateToFragment(R.id.content, fragment, false, true, null, null)
    }

    /**
     * Performs a fragment transaction.
     *
     * @param containerViewId The id of the container that the fragment will be placed in
     * @param fragment An instance of a fragment to be placed in the container
     * @param addToBackStack Flag to add the transaction to the back stack
     * @param clearContainer Flag to clear the container before the fragment transaction
     * @param tag An optional tag name for the fragment
     * @param fragmentManager The fragment manager to use for the transaction, or `null` to
     * use the activity's fragment manager
     */
    private fun navigateToFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        addToBackStack: Boolean,
        clearContainer: Boolean,
        @Nullable tag: String?,
        fragmentManager: FragmentManager?
    ) {
        val ft: FragmentTransaction =
            fragmentManager?.beginTransaction() ?: supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(null)
        }

        if (clearContainer) {
            ft.replace(containerViewId, fragment, tag)
        } else {
            ft.add(containerViewId, fragment, tag)
        }

        ft.commit()
    }
}
