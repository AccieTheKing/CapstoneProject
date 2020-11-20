package com.example.capstone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capstone.R
import com.example.capstone.models.Product
import com.example.capstone.ui.adapters.ProductAdapter
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {
    private val products = arrayListOf<Product>()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        productAdapter = ProductAdapter(products)
        rcMenuFragment.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rcMenuFragment.adapter = productAdapter
        rcMenuFragment.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        productAdapter.notifyDataSetChanged()
    }
}