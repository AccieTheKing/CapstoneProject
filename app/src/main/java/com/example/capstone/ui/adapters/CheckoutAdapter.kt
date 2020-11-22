package com.example.capstone.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.models.Product
import kotlinx.android.synthetic.main.item_checkout.view.*

class CheckoutAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_checkout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.txtInputAmountCheckoutText.setText("")
            itemView.txtCheckoutPrice.text = product.price.toString()
            Glide.with(itemView.context).load(product.banner_image)
                .into(itemView.ivProductCheckout)
        }
    }
}