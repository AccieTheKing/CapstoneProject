package com.example.capstone.ui.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.capstone.MainActivity
import com.example.capstone.R
import com.example.capstone.ui.viewmodels.SplashScreenViewModel
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(1024, 1024)
        setContentView(R.layout.activity_splash_screen)
        viewModel.getSplashDetails()

        viewModel.splashDetails.observe(this, {
            txtSplashscreenTitle.text = it.title
            txtViewCredits.text = it.credits
            Glide.with(this).load(it.background).into(splashscreenbackground)
            Timer("LeaveSplashScreen", false).schedule(6000) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        })

    }
}