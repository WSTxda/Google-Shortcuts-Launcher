package com.wstxda.gsl

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class GameManagerBrands(private val context: Context) {

    private val gameManagerIntents = listOf(
        // Xiaomi (Global/China)
        "com.miui.securitycenter" to "com.miui.gamebooster.ui.GameBoosterMainActivity",
        "com.miui.securitycenter" to "com.miui.gamebooster.mygame.MyGameActivity",

        // Samsung (Global/Europe/Asia)
        "com.samsung.android.game.gametools" to "com.samsung.android.game.gametools.main.MainActivity",
        "com.samsung.android.game.gamehome" to "com.samsung.android.game.gamehome.MainActivity",

        // Oppo (Global/China)
        "com.oppo.gamecenter" to "com.oppo.gamecenter.GameCenterActivity",
        "com.oppo.gamespace" to "com.oppo.gamespace.ui.GameSpaceMainActivity",

        // Vivo (Global/China)
        "com.vivo.game" to "com.vivo.game.GameActivity",
        "com.vivo.gamespace" to "com.vivo.gamespace.ui.GameSpaceMainActivity",

        // ASUS (Global)
        "com.asus.gamecenter" to "com.asus.gamecenter.GameCenterActivity",

        // Huawei (Global/China)
        "com.huawei.gameassistant" to "com.huawei.gameassistant.ui.MainActivity",
        "com.huawei.gameassistant" to "com.huawei.gameassistant.ui.GameSpaceMainActivity",

        // OnePlus (Global/China)
        "com.oneplus.game" to "com.oneplus.gamebox.GameBoxLauncherActivity",
        "com.oneplus.gamespace" to "com.oneplus.gamespace.ui.GameSpaceMainActivity",

        // Realme (Global/China)
        "com.realme.game" to "com.realme.game.GameSpaceMainActivity",
        "com.realme.gamespace" to "com.realme.gamespace.ui.GameSpaceMainActivity",

        // Lenovo (Global/China)
        "com.lenovo.gamezone" to "com.lenovo.gamezone.ui.GameZoneMainActivity",
        "com.lenovo.gamespace" to "com.lenovo.gamespace.ui.GameSpaceMainActivity",

        // ASUS ROG (Global)
        "com.rog.game" to "com.rog.game.launcher.MainActivity",

        // Black Shark (Global/China)
        "com.blackshark.gamespace" to "com.blackshark.gamespace.ui.MainActivity",
        "com.blackshark.gamespace" to "com.blackshark.gamespace.ui.GameSpaceMainActivity",

        // Nubia Red Magic (Global/China)
        "com.redmagic.gamecenter" to "com.redmagic.gamecenter.GameCenterMainActivity",
        "com.redmagic.gamecenter" to "com.redmagic.gamecenter.GameSpaceMainActivity",

        // Sony Xperia (Global)
        "com.sonymobile.gameenhancer" to "com.sonymobile.gameenhancer.GameEnhancerMainActivity",

        // Meizu (China)
        "com.meizu.flyme.gamecenter" to "com.meizu.flyme.gamecenter.GameCenterMainActivity",
        "com.meizu.gamespace" to "com.meizu.gamespace.ui.GameSpaceMainActivity",

        // ZTE (Global/China)
        "com.zte.gamespace" to "com.zte.gamespace.ui.GameSpaceMainActivity",

        // Motorola (Global)
        "com.motorola.gametime" to "com.motorola.gametime.ui.GameTimeMainActivity",
        "com.motorola.gametime" to "com.motorola.gametime.GameTimeMainActivity"
    )

    fun launchGameManager(): Boolean {
        for ((packageName, className) in gameManagerIntents) {
            if (tryStartActivityIntent(packageName, className)) {
                return true
            }
        }
        return false
    }

    private fun tryStartActivityIntent(packageName: String, className: String): Boolean {
        val intent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = ComponentName(packageName, className)
        }
        return tryStartActivity(intent)
    }

    private fun tryStartActivity(intent: Intent): Boolean {
        return try {
            context.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }
}