package com.example.capstone.ui.screens.menu.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.ui.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment : Fragment() {
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeProductDetail()
    }

    private fun observeProductDetail() {
        setFragmentResultListener(PRODUCT_KEY) { _, bundle ->
            bundle.getString(PRODUCT_BUNDLE_KEY)?.let {
                val productID = it.toInt()
                viewModel.getProduct(productID)
            }
        }

        viewModel.product.observe(viewLifecycleOwner, {
            Glide.with(this).load(it.banner_image).into(imgProductViewDetail)
            txtTitleProductDetail.text = it.title
            txtProductDescriptionDetailScreen.text = it.description
        })
    }

}