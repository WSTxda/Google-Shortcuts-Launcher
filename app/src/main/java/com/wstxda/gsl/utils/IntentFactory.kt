package com.wstxda.gsl.utils

import android.content.Intent

object IntentFactory {
    fun createMusicSearchIntent(): Intent =
        Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

    fun createSearchIntent(): Intent = Intent("android.search.action.GLOBAL_SEARCH").apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
}