package com.imooc.hi_library.demo

import android.app.Application
import com.google.gson.Gson
import com.imooc.hilibrary.log.HiConsolePrinter
import com.imooc.hilibrary.log.HiLogConfig
import com.imooc.hilibrary.log.HiLogConfig.JsonParser
import com.imooc.hilibrary.log.HiLogManager

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(
            object : HiLogConfig() {
                override fun injectJsonParser(): JsonParser? {
                    return JsonParser { src -> Gson().toJson(src) }
                }

                override fun getGlobalTag(): String {
                    return "MApplication"
                }

                override fun enable(): Boolean {
                    return true
                }

            }, HiConsolePrinter())
    }
}