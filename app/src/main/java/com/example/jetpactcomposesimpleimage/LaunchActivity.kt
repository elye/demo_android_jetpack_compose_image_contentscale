package com.example.jetpactcomposesimpleimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    fun launchNormal(view: View) {
        startActivity(Intent(this, NormalActivity::class.java))
    }
    fun launchCompose(view: View) {}
}