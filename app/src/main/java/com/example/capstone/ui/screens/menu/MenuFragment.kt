package com.example.capstone.ui.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstone.R
import com.example.capstone.ui.screens.menu.cart.CartFragment
import com.example.capstone.ui.screens.menu.product.ProductsFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {


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
        val productsFragment = ProductsFragment()
        val cartFragment = CartFragment()

        setCurrentFragment(productsFragment)

        tlMenuFragment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> setCurrentFragment(productsFragment)
                    1 -> setCurrentFragment(cartFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    /**
     * This method will set the given fragment as the shown fragment
     * inside the frame layout on the menuFragment
     */
    private fun setCurrentFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.flMenuContent, fragment)
            commit()
        }
    }
}