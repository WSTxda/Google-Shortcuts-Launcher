package com.wstxda.gsl.utils

import android.content.Intent

object IntentsFactory {

    fun createMusicSearchIntent(): Intent =
        Intent("com.google.android.googlequicksearchbox.MUSIC_SEARCH").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

    fun createSearchIntent(): Intent = Intent("android.intent.action.SEARCH_LONG_PRESS").apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
}