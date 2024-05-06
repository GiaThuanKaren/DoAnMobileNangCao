package com.example.standardblognote.services.momo.utils


object MoMoConfig {
    const val MOMO_APP_PAKAGE_STORE_DOWNLOAD = "com.mservice.momotransfer"
    const val MOMO_APP_PAKAGE_CLASS_PRODUCTION = "com.mservice.momotransfer" //production
    const val MOMO_APP_PAKAGE_CLASS_DEBUG =
        "com.mservice.debug" //DEBUG MODE IS NO LONGER SUPPORTED
    const val MOMO_APP_PAKAGE_CLASS_DEVELOPER =
        "vn.momo.platform.test" // App Test ver 3.0.12 or newest supported appid "vn.momo.platform.test", App Test ver 3.0.11 or lower using id "com.mservice";
    const val ACTION_SDK = "com.android.momo.SDK" //action mapping
    const val ACTION_PAYMENT = "com.android.momo.PAYMENT" //action payment
    const val ENVIRONMENT_DEBUG = 0 //Debug
    const val ENVIRONMENT_DEVELOPER = 1 //developer
    const val ENVIRONMENT_PRODUCTION = 2 //production
    const val ACTION_TYPE_GET_TOKEN = "gettoken" //action mapping
    const val ACTION_TYPE_LINK = "link" //action payment
}