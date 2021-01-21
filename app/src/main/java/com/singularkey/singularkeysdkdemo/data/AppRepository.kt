package com.singularkey.singularkeysdkdemo.data

import com.singularkey.base.repository.ApiListener
import com.singularkey.base.repository.BaseRepository
import com.singularkey.singularkeysdkdemo.network.Config
import com.singularkey.singularkeysdkdemo.network.model.PasswordLessRegisterRequest
import com.singularkey.singularkeysdkdemo.network.remote.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AppRepository : BaseRepository() {
    fun doPasswordLessRegister(
        request: PasswordLessRegisterRequest,
        listener: ApiListener,
    ): Disposable {
        return BaseRepository().createApiClient(Config.baseUrl, ApiInterface::class.java)
            .doPasswordLessRegister(request)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                listener.onSuccess(it)
            }, {
                listener.onError(it)
            })
    }


    fun doPasswordLessLogin(
        request: PasswordLessRegisterRequest,
        listener: ApiListener,
    ): Disposable {
        return BaseRepository().createApiClient(Config.baseUrl, ApiInterface::class.java)
            .doPasswordLessLogin(request)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                listener.onSuccess(it)
            }, {
                listener.onError(it)
            })
    }

    companion object {
        private var appRepository: AppRepository? = null

        fun instance(): AppRepository {
            if (appRepository == null) synchronized(AppRepository) {
                appRepository = AppRepository()
            }
            return appRepository!!
        }
    }
}