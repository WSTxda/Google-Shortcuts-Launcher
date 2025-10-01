package com.wstxda.gsl.utils

object ShortcutsIntents {
    val incognitoBrowserIntents = listOf(

        // Google Chrome
        "com.android.chrome" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",
        "com.chrome.canary" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",
        "com.chrome.beta" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",
        "com.chrome.dev" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher",

        // Brave Browser
        "com.brave.browser" to "org.chromium.chrome.browser.incognito.IncognitoTabLauncher"
    )

    val quickShareIntents = listOf(

        "com.google.android.gms" to "com.google.android.gms.nearby.sharing.send.SendActivity",
        "com.google.android.gms" to "com.google.android.gms.nearby.sharing.main.MainActivity",
        "com.google.android.gms" to "com.google.android.gms.nearby.sharing.main.SettingsMainActivity"
    )
}