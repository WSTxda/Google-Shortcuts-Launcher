@file:Suppress("DEPRECATION")

package com.wstxda.gsl.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mikepenz.aboutlibraries.LibsBuilder
import com.wstxda.gsl.R

class LibraryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setupToolbar(toolbar)

        val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar)
        collapsingToolbar.title = getString(R.string.pref_used_library_summary)

        if (savedInstanceState == null) {
            val fragment = LibsBuilder().withVersionShown(false).supportFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.library_fragment_container, fragment).commit()
        }
    }
}