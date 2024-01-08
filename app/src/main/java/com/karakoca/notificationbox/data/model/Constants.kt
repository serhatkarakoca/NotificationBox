package com.karakoca.notificationbox.data.model

object Constants {
    const val CHANNEL_ID = "TEST_NOTIFY"
    const val INTENT_ACTION_NOTIFICATION = "com.karakoca.notification"
    const val TODAY = "today"
    const val YESTERDAY = "yesterday"

    /***
     * Xiaomi
     */
    const val BRAND_XIAOMI = "xiaomi"
    const val PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter"
    const val PACKAGE_XIAOMI_COMPONENT =
        "com.miui.permcenter.autostart.AutoStartManagementActivity"


    /***
     * Honor
     */
    const val BRAND_HONOR = "honor"
    const val PACKAGE_HONOR_MAIN = "com.huawei.systemmanager"
    const val PACKAGE_HONOR_COMPONENT =
        "com.huawei.systemmanager.optimize.process.ProtectActivity"

    /**
     * Oppo
     */
    const val BRAND_OPPO = "oppo"
    const val PACKAGE_OPPO_MAIN = "com.coloros.safecenter"
    const val PACKAGE_OPPO_FALLBACK = "com.oppo.safe"
    const val PACKAGE_OPPO_COMPONENT =
        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
    const val PACKAGE_OPPO_COMPONENT_FALLBACK =
        "com.oppo.safe.permission.startup.StartupAppListActivity"
    const val PACKAGE_OPPO_COMPONENT_FALLBACK_A =
        "com.coloros.safecenter.startupapp.StartupAppListActivity"

    /**
     * Vivo
     */
    const val BRAND_VIVO = "vivo"
    const val PACKAGE_VIVO_MAIN = "com.iqoo.secure"
    const val PACKAGE_VIVO_FALLBACK = "com.vivo.perm;issionmanager"
    const val PACKAGE_VIVO_COMPONENT =
        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
    const val PACKAGE_VIVO_COMPONENT_FALLBACK =
        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
    const val PACKAGE_VIVO_COMPONENT_FALLBACK_A =
        "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"


    val brandList = listOf(BRAND_HONOR, BRAND_XIAOMI, BRAND_OPPO, BRAND_VIVO)
}