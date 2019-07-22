package com.brian.kotlinkoinmvvmcoroutines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brian.kotlinkoinmvvmcoroutines.R
import com.brian.kotlinkoinmvvmcoroutines.adapter.ItemAdapter
import com.brian.kotlinkoinmvvmcoroutines.model.Item
import com.brian.kotlinkoinmvvmcoroutines.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>(MainViewModel::class), ItemAdapter.ClickListener {
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter = ItemAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        // Initiate the observers on viewModel fields and then starts the API request
        initViewModel()
        swipeRefreshLayout.setOnRefreshListener { viewModel.getItems() }
    }

    private fun initViewModel() {
        viewModel.itemList.observe(this, Observer { itemList ->
            itemList?.let { swipeRefreshLayout.isRefreshing = false; itemAdapter.updateData(it) }
        })

        viewModel.itemListErrorEvent.observe(this, Observer { showError ->
            swipeRefreshLayout.isRefreshing = false;
            Toast.makeText(context, showError.getContentIfNotHandled(), Toast.LENGTH_SHORT).show()
        })

        viewModel.getItems()
    }

    override fun itemClick(item: Item) {
        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
