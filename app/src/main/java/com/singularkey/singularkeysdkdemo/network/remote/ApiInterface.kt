package com.singularkey.singularkeysdkdemo.network.remote

import com.singularkey.base.repository.SingularKeyApiConstants.Companion.PASSWORD_LESS_LOGIN
import com.singularkey.base.repository.SingularKeyApiConstants.Companion.PASSWORD_LESS_REGISTER
import com.singularkey.singularkeysdkdemo.network.model.PasswordLessRegisterRequest
import com.singularkey.singularkeysdkdemo.network.model.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST(PASSWORD_LESS_REGISTER)
    fun doPasswordLessRegister(@Body request: PasswordLessRegisterRequest): Observable<RegisterResponse>

    @POST(PASSWORD_LESS_LOGIN)
    fun doPasswordLessLogin(@Body request: PasswordLessRegisterRequest): Observable<RegisterResponse>

}
