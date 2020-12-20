package com.example.capstone.ui.screens.menu.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capstone.R
import com.example.capstone.models.Product
import com.example.capstone.repository.ProfileRepository
import com.example.capstone.ui.adapters.CheckoutAdapter
import com.example.capstone.ui.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {
    private var showCheckout: Boolean = false
    private val products = arrayListOf<Product>()
    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var checkoutAdapter: CheckoutAdapter

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
        observeCart()
    }

    private fun initView() {
        btnBuyProducts.isVisible = showCheckout
        checkoutAdapter = CheckoutAdapter(products, ::increaseProductAmount, ::decreaseProductAmount)
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

    private fun observeCart() {
        viewModel.getCart(ProfileRepository.phoneNumber.toInt())
        viewModel.cart.observe(viewLifecycleOwner, {
            if (it.size > 0) { // if any products selected
                txtSubTitleCartFragment.isVisible = false
            }
            products.clear()
            products.addAll(it)
            checkoutAdapter.notifyDataSetChanged()
        })
        viewModel.price.observe(viewLifecycleOwner, {
           if(it > 0.00){
               txtTotalPriceCartFragment.text =
                   getString(R.string.txtTotalPriceCartFragment, it.toString()) // set total price
               btnBuyProducts.isVisible = true // show checkout button
           }
        })
    }


    private fun increaseProductAmount(product: Product) {
        viewModel.increaseProductAmount(product.id, ProfileRepository.phoneNumber)
    }

    private fun decreaseProductAmount(product: Product) {
        viewModel.decreaseProductAmount(product.id, ProfileRepository.phoneNumber)
    }
}