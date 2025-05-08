@file:Suppress("DEPRECATION")

package com.wstxda.gsl.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mikepenz.aboutlibraries.LibsBuilder
import com.wstxda.gsl.R

class LibraryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        enableEdgeToEdgeNoContrast()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar)
        collapsingToolbar.title = getString(R.string.pref_used_library)

        if (savedInstanceState == null) {
            val fragment = LibsBuilder().supportFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.library_fragment_container, fragment).commit()
        }
    }

    private fun enableEdgeToEdgeNoContrast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
            )
            window.isNavigationBarContrastEnforced = false
        }
    }
}