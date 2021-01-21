package com.singularkey.singularkeysdkdemo.screens

import com.singularkey.base.data.model.BaseError
import com.singularkey.singularkeysdkdemo.network.model.RegisterResponse

interface SplashNavigator {
    fun onPasswordLessRegisterSuccess(response: RegisterResponse)
    fun onPasswordLessRegisterError(error: BaseError)
}