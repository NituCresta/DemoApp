package com.singularkey.singularkeysdkdemo.screens

import com.singularkey.base.base.BaseViewModel
import com.singularkey.base.repository.ApiListener
import com.singularkey.singularkeysdkdemo.data.AppRepository
import com.singularkey.singularkeysdkdemo.network.model.PasswordLessRegisterRequest
import com.singularkey.singularkeysdkdemo.network.model.RegisterResponse

class SplashViewModel : BaseViewModel<SplashNavigator>() {
    val repository = AppRepository.instance()

    fun doPasswordLessRegister(request: PasswordLessRegisterRequest) {
        getCompositeDisposable().add(
            repository.doPasswordLessRegister(request,
                object : ApiListener {
                    override fun onSuccess(successData: Any) {
                        navigator.onPasswordLessRegisterSuccess(successData as RegisterResponse)
                    }

                    override fun onError(error: Throwable) {
                        navigator.onPasswordLessRegisterError(repository.getErrorMessage(error)!!)
                    }
                })
        )
    }

    fun doPasswordLessLogin(request: PasswordLessRegisterRequest) {
        getCompositeDisposable().add(
            repository.doPasswordLessLogin(request,
                object : ApiListener {
                    override fun onSuccess(successData: Any) {
                        navigator.onPasswordLessRegisterSuccess(successData as RegisterResponse)
                    }

                    override fun onError(error: Throwable) {
                        navigator.onPasswordLessRegisterError(repository.getErrorMessage(error)!!)
                    }
                })
        )
    }
}