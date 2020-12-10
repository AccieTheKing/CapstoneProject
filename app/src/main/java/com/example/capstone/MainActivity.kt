package com.example.capstone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.capstone.ui.HomeFragment
import com.example.capstone.ui.MenuFragment
import com.example.capstone.ui.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    val profileIconId = R.id.personIconNav // profile nav item id
    val homeIconId = R.id.homeIconNav // home nav item id
    val menuIconId = R.id.menuIconNav // menu nav item id

    // Map object that maps id to corresponding fragment
    val navMapObject: Map<Int, Fragment> = mapOf(
        profileIconId to ProfileFragment(),
        homeIconId to HomeFragment(),
        menuIconId to MenuFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.selectedItemId = R.id.homeIconNav // sets the home as active

        // listens to clicks bottom nav
        bottomNavigationView.setOnNavigationItemSelectedListener {
            val currentSelectedNavItem = bottomNavigationView.selectedItemId

            // prevent to navigate to same screen, because it crashed the app
            if (it.itemId != currentSelectedNavItem) {
                navController.navigate(
                    onNavigate(
                        currentSelectedNavItem,
                        navMapObject[it.itemId]
                    )
                )
            }
            true
        }
    }

    /**
     * This method will check the current selected nav item and returns the navigation link to
     * that Fragment
     */
    private fun onNavigate(currentNavItem: Int, fragment: Fragment?): Int {
        if (fragment != null) {
            return when (currentNavItem) {
                profileIconId -> { // bottom nav item id
                    when (fragment) {
                        navMapObject[homeIconId] -> R.id.action_profileFragment_to_homeFragment
                        navMapObject[menuIconId] -> R.id.action_profileFragment_to_menuFragment
                        else -> 0 // TODO: navigate to error page
                    }
                }
                homeIconId -> {
                    when (fragment) { // to which fragment to navigate to
                        navMapObject[profileIconId] -> R.id.action_homeFragment_to_profileFragment
                        navMapObject[menuIconId] -> R.id.action_homeFragment_to_menuFragment
                        else -> 0 // TODO: navigate to error page
                    }
                }
                menuIconId -> {
                    when (fragment) { // to which fragment to navigate to
                        navMapObject[homeIconId] -> R.id.action_menuFragment_to_homeFragment
                        navMapObject[profileIconId] -> R.id.action_menuFragment_to_profileFragment
                        else -> 0 // TODO: navigate to error page
                    }
                }
                else -> {
                    Log.d("Nav error", "Navigation to $fragment not implemented")
                }
            }
        }

        return 0 // TODO: error fragment does not exist
    }
}