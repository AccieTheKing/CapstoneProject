package com.example.capstone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capstone.R
import com.example.capstone.models.Product
import com.example.capstone.ui.adapters.CheckoutAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {
    private var showCheckout: Boolean = false
    private val products = arrayListOf<Product>()
    private lateinit var checkoutAdapter: CheckoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnBuyProducts.isVisible = showCheckout
        checkoutAdapter = CheckoutAdapter(products)
        rcCheckoutList.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rcCheckoutList.adapter = checkoutAdapter
        rcCheckoutList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        checkoutAdapter.notifyDataSetChanged()
    }
}