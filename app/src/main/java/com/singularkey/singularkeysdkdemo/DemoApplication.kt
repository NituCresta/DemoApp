package com.singularkey.singularkeysdkdemo

import com.singularkey.base.BaseApplication

class DemoApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    fun saveToken(token: String) {
        authToken = token
    }

    companion object {
        private var instance: DemoApplication? = null
        fun instance(): DemoApplication {
            if (instance == null) synchronized(DemoApplication) {
                instance = DemoApplication()
            }
            return instance!!
        }
    }

}