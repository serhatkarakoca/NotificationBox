package com.karakoca.notificationbox.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.karakoca.notificationbox.model.local.Constants.BRAND_HONOR
import com.karakoca.notificationbox.model.local.Constants.BRAND_OPPO
import com.karakoca.notificationbox.model.local.Constants.BRAND_VIVO
import com.karakoca.notificationbox.model.local.Constants.BRAND_XIAOMI
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_HONOR_COMPONENT
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_HONOR_MAIN
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_OPPO_COMPONENT
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_OPPO_COMPONENT_FALLBACK
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_OPPO_COMPONENT_FALLBACK_A
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_OPPO_FALLBACK
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_OPPO_MAIN
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_VIVO_COMPONENT
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_VIVO_COMPONENT_FALLBACK
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_VIVO_COMPONENT_FALLBACK_A
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_VIVO_FALLBACK
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_VIVO_MAIN
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_XIAOMI_COMPONENT
import com.karakoca.notificationbox.model.local.Constants.PACKAGE_XIAOMI_MAIN


class AutoStartSetting {
    companion object {
        fun startAutoStartDialog(context: Context, brand: String) {
            when (brand) {
                BRAND_XIAOMI -> autoStartXiaomi(context)
                BRAND_HONOR -> autoStartHonor(context)
                BRAND_OPPO -> autoStartOppo(context)
                BRAND_VIVO -> autoStartVivo(context)
            }
        }

        private fun autoStartXiaomi(context: Context) {

            try {
                startIntent(context, PACKAGE_XIAOMI_MAIN, PACKAGE_XIAOMI_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


        private fun autoStartHonor(context: Context) {

            try {
                startIntent(context, PACKAGE_HONOR_MAIN, PACKAGE_HONOR_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        private fun autoStartOppo(context: Context) {

            try {
                startIntent(context, PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    startIntent(
                        context,
                        PACKAGE_OPPO_FALLBACK,
                        PACKAGE_OPPO_COMPONENT_FALLBACK
                    )
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    try {
                        startIntent(
                            context,
                            PACKAGE_OPPO_MAIN,
                            PACKAGE_OPPO_COMPONENT_FALLBACK_A
                        )
                    } catch (exx: Exception) {
                        exx.printStackTrace()
                    }

                }
            }
        }

        private fun autoStartVivo(context: Context) {

            try {
                startIntent(context, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    startIntent(
                        context,
                        PACKAGE_VIVO_FALLBACK,
                        PACKAGE_VIVO_COMPONENT_FALLBACK
                    )
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    try {
                        startIntent(
                            context,
                            PACKAGE_VIVO_MAIN,
                            PACKAGE_VIVO_COMPONENT_FALLBACK_A
                        )
                    } catch (exx: Exception) {
                        exx.printStackTrace()
                    }
                }

            }
        }

        private fun startIntent(context: Context, packageName: String, componentName: String) {
            try {
                val intent = Intent()
                intent.setComponent(ComponentName(packageName, componentName))
                context.startActivity(intent)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}