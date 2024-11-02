package com.example.heroapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.heroapp.R
import com.example.heroapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val backgroundImage: ImageView = binding.marvelLogo
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)

        backgroundImage.startAnimation(slideAnimation)
        window.decorView.postDelayed({

            startActivity(Intent(this, HomeActivity::class.java))
            finish()

        }, 3000)
    }
}