package com.example.capstone.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.models.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(
    private val products: List<Product>,
    private val addToCart: (Product) -> Unit,
    private val moreInfo: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btnBuyAddProduct.setOnClickListener {
                addToCart(products[adapterPosition])
            }
            itemView.btnMoreInfo.setOnClickListener {
                moreInfo(products[adapterPosition])
            }
        }

        fun bind(product: Product) {
            itemView.txtViewProductTitle.text = product.title
            itemView.txtProductPrice.text = String.format("Price: %s", product.price)
            Glide.with(itemView.context).load(product.banner_image)
                .into(itemView.imgPreviewProduct)
        }
    }
}