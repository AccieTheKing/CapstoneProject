package com.example.capstone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment() // holds the home fragment
        val profileFragment = ProfileFragment() // holds the profile fragment
        val menuFragment = MenuFragment() // holds the menu fragment

        setCurrentFragment(homeFragment) // on create set the home as active
        bottomNavigationView.selectedItemId = R.id.homeIconNav // sets the home as active

        // listens to clicks bottom nav
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeIconNav -> setCurrentFragment(homeFragment)
                R.id.personIconNav -> setCurrentFragment(profileFragment)
                R.id.menuIconNav -> setCurrentFragment(menuFragment)
            }
            true
        }
    }


    /**
     * This method will set the given fragment as the shown fragment
     * inside the frame layout on the activity home screen
     */
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}