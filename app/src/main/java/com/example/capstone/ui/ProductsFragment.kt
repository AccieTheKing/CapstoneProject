package com.example.capstone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capstone.R
import com.example.capstone.models.Product
import com.example.capstone.repository.ProfileRepository
import com.example.capstone.ui.adapters.ProductAdapter
import com.example.capstone.ui.viewmodels.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_products.*


const val PRODUCT_KEY = "PRODUCT_KEY"
const val PRODUCT_BUNDLE_KEY = "PRODUCT_BUNDLE_KEY"

class ProductsFragment : Fragment() {
    private val products = arrayListOf<Product>()
    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeProducts()
        observeErrors()
    }

    private fun initView() {
        productAdapter = ProductAdapter(products, ::addToCart, ::goToProductDetail)
        rcProductsFragment.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rcProductsFragment.adapter = productAdapter
        rcProductsFragment.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        productAdapter.notifyDataSetChanged()
    }

    private fun observeProducts() {
        viewModel.getProducts()
        viewModel.products.observe(viewLifecycleOwner, {
            products.clear()
            products.addAll(it)
            productAdapter.notifyDataSetChanged()
        })

    }

    private fun observeAdditions(productTitle: String) {
        viewModel.success.observe(viewLifecycleOwner, {
            if (it) {
                Snackbar.make(
                    this.requireView(),
                    "Successful added $productTitle to cart",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun observeErrors() {
        viewModel.errorText.observe(viewLifecycleOwner, {
            Snackbar.make(
                this.requireView(),
                it.toString(),
                Snackbar.LENGTH_SHORT
            ).show()
        })
    }

    private fun goToProductDetail(product: Product) {
        setFragmentResult(
            PRODUCT_KEY,
            bundleOf(Pair(PRODUCT_BUNDLE_KEY, product.id.toString()))
        )
        findNavController().navigate(R.id.action_menuFragment_to_productDetailFragment)
    }

    private fun addToCart(product: Product) {
        viewModel.addProductToCart(
            product,
            ProfileRepository.phoneNumber
        )
        observeAdditions(product.title)
    }

}