package com.wstxda.gsl.utils

object IncognitoBrowsersPackages {
    val incognitoLauncherIntents = listOf(

        // Google Chrome
        "com.android.chrome" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",
        "com.chrome.canary" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",
        "com.chrome.beta" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",
        "com.chrome.dev" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",

        // Brave Browser
        "com.brave.browser" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher"
    )
}