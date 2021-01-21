package com.singularkey.singularkeysdkdemo.network.model

import com.singularkey.singularkeysdkdemo.network.model.SingularKeySdkToken

class RegisterResponse {
    var message: String? = null
    var accessToken: String? = null
    var singularKeySdkToken: SingularKeySdkToken? = null
    var statusCode: Int? = 0
    var flowId: String? = null
    var policyId: String? = null
}